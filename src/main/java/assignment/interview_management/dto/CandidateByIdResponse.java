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
public class CandidateByIdResponse {

    private Long id;

    private String fullName;

    private String email;

    private LocalDate dateOfBirth;

    private String address;

    private String phoneNumber;

    private String gender;

    private String filename;

    private String cvFilePath;

    private String position;

    private Integer yearOfExperience;

    private List<String> skills;

    private String level;

    private String note;

    private String status;
}
