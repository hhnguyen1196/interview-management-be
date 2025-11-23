package assignment.interview_management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class SaveCandidateRequest {

    private Long id;

    private String fullName;

    private String email;

    private LocalDate dateOfBirth;

    private String address;

    private String phoneNumber;

    private String gender;

    @ToString.Exclude
    private MultipartFile cvAttachment;

    private String position;

    private Integer yearOfExperience;

    private List<String> skills;

    private String level;

    private String note;
}
