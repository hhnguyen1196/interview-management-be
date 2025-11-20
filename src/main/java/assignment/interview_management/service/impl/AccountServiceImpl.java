package assignment.interview_management.service.impl;

import assignment.interview_management.dto.AccountByIdResponse;
import assignment.interview_management.dto.AccountListResponse;
import assignment.interview_management.dto.GetAllAccountQuery;
import assignment.interview_management.dto.SaveAccountRequest;
import assignment.interview_management.entity.Account;
import assignment.interview_management.repository.AccountRepository;
import assignment.interview_management.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

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
                .password(request.getPassword())
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

    }

    @Override
    public AccountByIdResponse getAccountById(Long id) {
        return null;
    }
}
