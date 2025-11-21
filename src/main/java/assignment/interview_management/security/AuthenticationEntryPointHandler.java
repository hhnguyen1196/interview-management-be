package assignment.interview_management.security;

import assignment.interview_management.dto.ApiResponseError;
import assignment.interview_management.enums.ErrorCode;
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
        builder.error(ErrorCode.UNAUTHORIZED.name());
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
