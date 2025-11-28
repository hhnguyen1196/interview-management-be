package assignment.interview_management.service.impl;

import assignment.interview_management.dto.ChangePasswordRequest;
import assignment.interview_management.dto.LoginRequest;
import assignment.interview_management.dto.LoginResponse;
import assignment.interview_management.dto.SendMailRequest;
import assignment.interview_management.entity.Account;
import assignment.interview_management.exceptions.AuthException;
import assignment.interview_management.exceptions.BusinessException;
import assignment.interview_management.repository.AccountRepository;
import assignment.interview_management.security.TokenProvider;
import assignment.interview_management.service.AuthService;
import assignment.interview_management.service.EmailService;
import assignment.interview_management.utils.PasswordUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import java.util.Optional;

/**
 * Triển khai các nghiệp vụ xác thực người dùng (AuthService).
 *
 * <p>
 * Lớp này đảm nhiệm:
 * - Xử lý đăng nhập và sinh JWT token
 * - Thay đổi mật khẩu người dùng hiện tại
 * - Cấp lại mật khẩu mới và gửi email thông báo
 * Mọi thao tác liên quan đến xác thực đều được kiểm soát trong transaction để đảm bảo
 * tính toàn vẹn dữ liệu và tránh tình trạng cập nhật thiếu nhất quán.
 * </p>
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private AccountRepository accountRepository;

    private AuthenticationManager authenticationManager;

    private TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder;

    private EmailService emailService;

    /**
     * Xử lý đăng nhập hệ thống:
     * - Xác thực username/password bằng AuthenticationManager
     * - Sinh token JWT nếu thông tin hợp lệ
     *
     * @param request Thông tin đăng nhập
     * @return LoginResponse chứa JWT token
     */
    @Override
    public LoginResponse login(LoginRequest request) {
        log.info("request: {}", request);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));
        return LoginResponse.builder()
                .token(tokenProvider.generateToken(authentication))
                .build();
    }

    /**
     * Thay đổi mật khẩu của người dùng hiện tại.
     * Quy trình:
     * - Lấy username từ SecurityContext
     * - Kiểm tra tài khoản tồn tại
     * - So sánh mật khẩu cũ
     * - Mã hóa và cập nhật mật khẩu mới
     *
     * @param request Thông tin mật khẩu cũ & mật khẩu mới
     * @throws AuthException     nếu tài khoản không tồn tại hoặc không hợp lệ
     * @throws BusinessException nếu mật khẩu cũ không đúng
     */
    @Override
    public void changePassword(ChangePasswordRequest request) {
        log.info("request: ChangePasswordRequest");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isEmpty()) {
            throw new AuthException("Thông tin xác thực không đúng. Vui lòng thử lại");
        }
        Account account = accountOptional.get();
        if (!passwordEncoder.matches(request.getOldPassword(), account.getPassword())) {
            throw new BusinessException("Mật khẩu cũ không chính xác");
        }
        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
        accountRepository.save(account);
    }

    /**
     * Xử lý quy trình quên mật khẩu:
     * - Kiểm tra tài khoản theo username
     * - Sinh mật khẩu mới ngẫu nhiên
     * - Gửi email cấp lại mật khẩu cho người dùng
     * - Cập nhật mật khẩu mới (đã mã hóa) vào hệ thống
     *
     * @param username Username của tài khoản cần cấp lại mật khẩu
     * @throws BusinessException nếu username không tồn tại
     */

    @Override
    public void forgotPassword(String username) {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isEmpty()) {
            throw new BusinessException("Username không tồn tại");
        }
        Account account = accountOptional.get();

        // Tạo nội dung email gửi mật khẩu mới
        Context context = new Context();
        context.setVariable("fullName", account.getFullName());
        context.setVariable("username", account.getUsername());
        String password = PasswordUtils.generatePassword(12);
        context.setVariable("password", password);
        String[] to = new String[]{account.getEmail()};
        String subject = "THÔNG BÁO CẤP LẠI MẬT KHẨU";
        SendMailRequest sendMailRequest = SendMailRequest.builder()
                .to(to)
                .subject(subject)
                .build();
        emailService.sendEmail(sendMailRequest, "password-reset", context);

        // Cập nhật lại mật khẩu mới
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);
    }

}
