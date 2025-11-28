package assignment.interview_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountByIdResponse {

    private Long id;

    private String username;

    private String fullName;

    private String gender;

    private LocalDate dateOfBirth;

    private String email;

    private String address;

    private String phoneNumber;

    private String role;

    private Boolean isActive;
}
