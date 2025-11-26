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

    /**
     * Lấy danh sách tài khoản theo từ khóa tìm kiếm và phân trang.
     *
     * @param search Từ khóa dùng để tìm kiếm tài khoản (theo tên hoặc các thuộc tính liên quan).
     * @param page   Chỉ số trang (bắt đầu từ 0) để phân trang.
     * @param size   Số lượng phần tử trên một trang.
     * @return ResponseEntity chứa AccountListResponse bao gồm danh sách tài khoản.
     */
    @GetMapping("/accounts")
    public ResponseEntity<AccountListResponse> getAllAccounts(@RequestParam("search") String search,
                                                              @RequestParam("page") Integer page,
                                                              @RequestParam("size") Integer size) {
        log("GetAllAccounts " + search);
        return ResponseEntity.ok(accountService.getAllAccounts(search, page, size));
    }

    /**
     * Tạo mới hoặc cập nhật thông tin tài khoản dựa trên sự tồn tại của ID.
     *
     * @param request Đối tượng chứa thông tin tài khoản:
     *                - Nếu id == null → tạo tài khoản mới.
     *                - Nếu id != null → cập nhật tài khoản hiện có.
     * @return ResponseEntity với:
     *         - HTTP 201 CREATED nếu tạo mới.
     *         - HTTP 204 NO CONTENT nếu cập nhật.
     */
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

    /**
     * Lấy thông tin chi tiết của một tài khoản theo ID.
     *
     * @param id ID duy nhất của tài khoản cần lấy thông tin.
     * @return ResponseEntity chứa AccountByIdResponse với thông tin chi tiết tài khoản.
     */
    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountByIdResponse> getAccountById(@PathVariable Long id) {
        log(id);
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping("/accounts/info")
    public ResponseEntity<AccountInfoResponse> getAccountInfo() {
        return ResponseEntity.ok(accountService.getAccountInfo());
    }

    private void log(Object o) {
        log.info("request: {}", o);
    }
}
