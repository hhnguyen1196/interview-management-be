package assignment.interview_management.security;

import assignment.interview_management.dto.ApiResponseError;
import assignment.interview_management.enums.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class AccessDeniedErrorHandler implements AccessDeniedHandler {

    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
            throws IOException {
        ApiResponseError.ApiResponseErrorBuilder builder = ApiResponseError.builder();
        builder.timestamp(LocalDateTime.now());
        builder.status(HttpStatus.FORBIDDEN.value());
        builder.error(ErrorCode.FORBIDDEN.name());
        builder.message("Truy cập bị từ chối. Bạn không có quyền truy cập vào tài nguyên này");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(objectMapper.writeValueAsString(builder.build()));
    }
}
