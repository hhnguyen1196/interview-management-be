package assignment.interview_management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ForgotPasswordRequest {

    private String username;

    private String email;
}
