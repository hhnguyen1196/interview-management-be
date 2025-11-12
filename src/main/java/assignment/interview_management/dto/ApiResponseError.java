package assignment.interview_management.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiResponseError {

    private int status;

    private String message;

    private LocalDateTime timestamp;
}
