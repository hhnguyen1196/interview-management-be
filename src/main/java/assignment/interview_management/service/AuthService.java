package assignment.interview_management.service;

import assignment.interview_management.dto.ChangePasswordRequest;
import assignment.interview_management.dto.LoginRequest;
import assignment.interview_management.dto.LoginResponse;

/**
 * Service định nghĩa các chức năng xác thực (authentication)
 * mà hệ thống cung cấp cho người dùng.
 *
 * <p>
 * Bao gồm các nghiệp vụ chính:
 * - Đăng nhập để lấy token
 * - Thay đổi mật khẩu
 * - Quên mật khẩu và cấp lại mật khẩu mới
 * </p>
 */
public interface AuthService {

    /**
     * Xử lý đăng nhập vào hệ thống bằng username và password hợp lệ.
     *
     * @param request Thông tin đăng nhập (username, password)
     * @return LoginResponse chứa token JWT phục vụ xác thực
     */
    LoginResponse login(LoginRequest request);

    /**
     * Thay đổi mật khẩu của người dùng hiện tại.
     *
     * @param request Thông tin mật khẩu cũ và mật khẩu mới
     */
    void changePassword(ChangePasswordRequest request);

    /**
     * Khởi tạo quy trình cấp lại mật khẩu khi người dùng quên mật khẩu.
     *
     * @param username Tên đăng nhập của tài khoản cần cấp lại mật khẩu
     */
    void forgotPassword(String username);
}
