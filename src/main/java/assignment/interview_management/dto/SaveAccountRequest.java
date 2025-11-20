package assignment.interview_management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class SaveAccountRequest {

    private Long id;

    private String username;

    @ToString.Exclude
    private String password;

    private String fullName;

    private String gender;

    private LocalDate dateOfBirth;

    private String email;

    private String address;

    private String phoneNumber;

    private String department;

    private String role;
}
