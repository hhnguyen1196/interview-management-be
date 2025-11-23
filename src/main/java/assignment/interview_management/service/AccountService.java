package assignment.interview_management.service;

import assignment.interview_management.dto.AccountByIdResponse;
import assignment.interview_management.dto.AccountListResponse;
import assignment.interview_management.dto.SaveAccountRequest;

public interface AccountService {

    AccountListResponse getAllAccounts(String search, Integer page, Integer size);

    void createAccount(SaveAccountRequest request);

    void updateAccount(SaveAccountRequest request);

    AccountByIdResponse getAccountById(Long id);
}
