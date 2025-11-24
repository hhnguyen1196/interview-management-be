package assignment.interview_management.service.impl;

import assignment.interview_management.dto.ChangePasswordRequest;
import assignment.interview_management.dto.LoginRequest;
import assignment.interview_management.dto.LoginResponse;
import assignment.interview_management.entity.Account;
import assignment.interview_management.exceptions.AuthException;
import assignment.interview_management.exceptions.BusinessException;
import assignment.interview_management.repository.AccountRepository;
import assignment.interview_management.security.TokenProvider;
import assignment.interview_management.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private AccountRepository accountRepository;

    private AuthenticationManager authenticationManager;

    private TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {
        log.info("request: {}", request);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));
        return LoginResponse.builder()
                .token(tokenProvider.generateToken(authentication))
                .build();
    }

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
}
