package assignment.interview_management.exceptions;

import assignment.interview_management.dto.ApiResponseError;
import assignment.interview_management.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponseError> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponseError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(ErrorCode.ENTITY_NOT_FOUND.name())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiResponseError> handleAuthException(AuthException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponseError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .error(ErrorCode.UNAUTHORIZED.name())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(ForbiddenOperationException.class)
    public ResponseEntity<ApiResponseError> handleAuthException(ForbiddenOperationException e) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK.value())
                        .error(HttpStatus.OK.getReasonPhrase())
                        .message(e.getMessage())
                        .build());
    }
}
