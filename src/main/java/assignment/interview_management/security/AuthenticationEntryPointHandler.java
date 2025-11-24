package assignment.interview_management.security;

import assignment.interview_management.dto.ApiResponseError;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * Xử lý lỗi khi người dùng chưa xác thực (unauthenticated) hoặc thông tin xác thực không hợp lệ.
 *
 * <p>
 * Class này implement {@link AuthenticationEntryPoint} của Spring Security.
 * Khi một request không được xác thực, phương thức {@link #commence(HttpServletRequest, HttpServletResponse, AuthenticationException)}
 * sẽ được gọi, trả về response JSON với thông tin lỗi.
 * </p>
 *
 * <p>
 * JSON trả về bao gồm:
 * <ul>
 *     <li>timestamp: thời gian xảy ra lỗi</li>
 *     <li>status: HTTP status (401)</li>
 *     <li>error: mã lỗi (UNAUTHORIZED)</li>
 *     <li>message: thông báo chi tiết cho client</li>
 * </ul>
 * </p>
 */
@Component
@AllArgsConstructor
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException {
        ApiResponseError.ApiResponseErrorBuilder builder = ApiResponseError.builder();
        builder.timestamp(LocalDateTime.now());
        builder.status(HttpStatus.UNAUTHORIZED.value());
        builder.error(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        if (exception instanceof BadCredentialsException) {
            builder.message("Tên đăng nhập hoặc mật khẩu không hợp lệ");
        } else {
            builder.message("Thông tin xác thực không đúng. Vui lòng thử lại");
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(objectMapper.writeValueAsString(builder.build()));
    }
}
