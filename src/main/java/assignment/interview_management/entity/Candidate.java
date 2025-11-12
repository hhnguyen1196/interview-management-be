package assignment.interview_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Candidate extends AuditListener<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "gender")
    private String gender;

    @Column(name = "cv_file_path")
    private String cvFilePath;

    @Column(name = "filename")
    private String filename;

    @Column(name = "position")
    private String position;

    @Column(name = "year_of_experience")
    private Integer yearOfExperience;

    @Column(name = "level")
    private String level;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private String status;
}
