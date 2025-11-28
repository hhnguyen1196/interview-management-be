package assignment.interview_management.service.impl;

import assignment.interview_management.dto.*;
import assignment.interview_management.entity.Account;
import assignment.interview_management.exceptions.BusinessException;
import assignment.interview_management.repository.AccountRepository;
import assignment.interview_management.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Triển khai (implementation) của AccountService,
 * xử lý toàn bộ nghiệp vụ quản lý tài khoản người dùng.
 *
 * <p>
 * Lớp này đảm nhiệm các chức năng:
 * - Lấy danh sách tài khoản kèm phân trang
 * - Tạo tài khoản mới (kèm mã hóa mật khẩu)
 * - Cập nhật thông tin tài khoản
 * - Lấy thông tin chi tiết theo ID
 * Mọi thao tác đều được quản lý trong transaction để đảm bảo tính toàn vẹn dữ liệu.
 * </p>
 */
@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private PasswordEncoder passwordEncoder;

    /**
     * Lấy danh sách tài khoản dựa trên từ khóa tìm kiếm,
     * có hỗ trợ phân trang bằng limit/offset.
     *
     * @param search Từ khóa tìm kiếm
     * @param page   Số trang (0-based)
     * @param size   Số lượng phần tử trên mỗi trang
     * @return Danh sách tài khoản + tổng số lượng bản ghi
     */
    @Override
    public AccountListResponse getAllAccounts(String search, Integer page, Integer size) {
        List<GetAllAccountQuery> accountList = accountRepository.getAllAccounts(search, size, page * size);
        int totalElements = accountRepository.countAccount(search);
        return AccountListResponse.builder()
                .accountList(accountList.stream()
                        .map(o -> AccountListResponse.Account.builder()
                                .id(o.getId())
                                .username(o.getUsername())
                                .fullName(o.getFullName())
                                .phoneNumber(o.getPhoneNumber())
                                .email(o.getEmail())
                                .role(o.getRole())
                                .isActive(o.getIsActive())
                                .build())
                        .toList())
                .totalElements(totalElements)
                .build();
    }

    /**
     * Tạo mới tài khoản trong hệ thống.
     * Thực hiện kiểm tra username đã tồn tại hay chưa.
     * Nếu chưa tồn tại → mã hóa password và lưu vào database.
     *
     * @param request Dữ liệu tài khoản cần tạo mới
     * @throws BusinessException nếu username đã tồn tại
     */
    @Override
    public void createAccount(SaveAccountRequest request) {
        Integer accountExists = accountRepository.existsAccount(request.getUsername());
        if (Objects.nonNull(accountExists)) {
            throw new BusinessException("Tài khoản này đã tồn tại trong hệ thống");
        }
        accountRepository.save(Account.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .email(request.getEmail())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .department(request.getDepartment())
                .role(request.getRole())
                .isActive(Boolean.TRUE)
                .build());
    }

    /**
     * Cập nhật thông tin tài khoản có sẵn trong hệ thống.
     *
     * @param request Dữ liệu cập nhật (bao gồm ID tài khoản)
     * @throws BusinessException nếu tài khoản không tồn tại
     */
    @Override
    public void updateAccount(SaveAccountRequest request) {
        Optional<Account> accountOptional = accountRepository.findById(request.getId());
        if (accountOptional.isEmpty()) {
            throw new BusinessException("Tài khoản không tồn tai");
        }
        Account account = accountOptional.get();
        account.setFullName(request.getFullName());
        account.setGender(request.getGender());
        account.setDateOfBirth(request.getDateOfBirth());
        account.setEmail(request.getEmail());
        account.setAddress(request.getAddress());
        account.setPhoneNumber(request.getPhoneNumber());
        account.setDepartment(request.getDepartment());
        account.setRole(request.getRole());
        account.setIsActive(request.getIsActive());
        accountRepository.save(account);
    }

    /**
     * Lấy thông tin chi tiết tài khoản theo ID.
     *
     * @param id ID tài khoản
     * @return Thông tin tài khoản tương ứng
     * @throws BusinessException nếu tài khoản không tồn tại
     */
    @Override
    public AccountByIdResponse getAccountById(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isEmpty()) {
            throw new BusinessException("Tài khoản không tồn tai");
        }
        Account account = accountOptional.get();
        return AccountByIdResponse.builder()
                .id(account.getId())
                .username(account.getUsername())
                .fullName(account.getFullName())
                .gender(account.getGender())
                .dateOfBirth(account.getDateOfBirth())
                .email(account.getEmail())
                .address(account.getAddress())
                .phoneNumber(account.getPhoneNumber())
                .role(account.getRole())
                .isActive(account.getIsActive())
                .build();
    }

    @Override
    public AccountInfoResponse getAccountInfo() {
        return AccountInfoResponse.builder()
                .username(SecurityContextHolder.getContext().getAuthentication().getName())
                .build();
    }
}
