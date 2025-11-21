package assignment.interview_management.service.impl;

import assignment.interview_management.dto.AccountByIdResponse;
import assignment.interview_management.dto.AccountListResponse;
import assignment.interview_management.dto.GetAllAccountQuery;
import assignment.interview_management.dto.SaveAccountRequest;
import assignment.interview_management.entity.Account;
import assignment.interview_management.exceptions.EntityNotFoundException;
import assignment.interview_management.repository.AccountRepository;
import assignment.interview_management.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private PasswordEncoder passwordEncoder;

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
                                .gender(o.getGender())
                                .email(o.getEmail())
                                .department(o.getDepartment())
                                .isActive(o.getIsActive())
                                .build())
                        .toList())
                .totalElements(totalElements)
                .build();
    }

    @Override
    public void createAccount(SaveAccountRequest request) {
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

    @Override
    public void updateAccount(SaveAccountRequest request) {
        Optional<Account> accountOptional = accountRepository.findById(request.getId());
        if (accountOptional.isEmpty()) {
            throw new EntityNotFoundException("Tài khoản không tồn tai");
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

    @Override
    public AccountByIdResponse getAccountById(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isEmpty()) {
            throw new EntityNotFoundException("Tài khoản không tồn tai");
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
                .department(account.getDepartment())
                .role(account.getRole())
                .isActive(account.getIsActive())
                .build();
    }
}
