package assignment.interview_management.controller;

import assignment.interview_management.dto.ChangePasswordRequest;
import assignment.interview_management.dto.LoginRequest;
import assignment.interview_management.dto.LoginResponse;
import assignment.interview_management.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller xử lý các API liên quan đến xác thực người dùng
 * như đăng nhập, đổi mật khẩu và quên mật khẩu.
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    /**
     * Xử lý yêu cầu đăng nhập vào hệ thống.
     *
     * @param request Đối tượng chứa thông tin đăng nhập gồm username và password.
     * @return ResponseEntity chứa LoginResponse với Token hoặc thông tin đăng nhập hợp lệ.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        log(request);
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Thay đổi mật khẩu cho người dùng.
     *
     * @param request Đối tượng chứa thông tin username, mật khẩu cũ và mật khẩu mới.
     * @return ResponseEntity với HTTP 200 OK nếu thay đổi mật khẩu thành công.
     */
    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest request) {
        log(request);
        authService.changePassword(request);
        return ResponseEntity.noContent().build();
    }

    /**
     * Xử lý yêu cầu quên mật khẩu theo username.
     *
     * @param username Tên đăng nhập của người dùng muốn reset mật khẩu.
     * @return ResponseEntity với HTTP 200 OK nếu xử lý yêu cầu thành công.
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestParam String username) {
        log("ForgotPassword");
        authService.forgotPassword(username);
        return ResponseEntity.noContent().build();
    }

    private void log(Object o) {
        log.info("request: {}", o);
    }
}
