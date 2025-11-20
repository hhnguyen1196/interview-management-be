package assignment.interview_management.controller;

import assignment.interview_management.dto.AccountByIdResponse;
import assignment.interview_management.dto.AccountListResponse;
import assignment.interview_management.dto.SaveAccountRequest;
import assignment.interview_management.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/accounts")
    public ResponseEntity<AccountListResponse> getAllAccounts(@RequestParam("search") String search,
                                                              @RequestParam("page") Integer page,
                                                              @RequestParam("size") Integer size) {
        log("getAllAccounts " + search);
        return ResponseEntity.ok(accountService.getAllAccounts(search, page, size));
    }

    @PostMapping("/accounts")
    public ResponseEntity<Void> saveAccount(@RequestBody SaveAccountRequest request) {
        log(request);
        boolean isCreated = request.getId() == null;
        if (isCreated) {
            accountService.createAccount(request);
        } else {
            accountService.updateAccount(request);
        }
        return isCreated ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.noContent().build();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountByIdResponse> getAccountById(@PathVariable Long id) {
        log(id);
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    private void log(Object o) {
        log.info("request: {}", o);
    }
}
