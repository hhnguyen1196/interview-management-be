package assignment.interview_management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class SaveJobRequest {

    private Long id;

    private String title;

    private List<String> skills;

    private String level;

    private String salary;

    private LocalDate startDate;

    private LocalDate endDate;

    private String workingAddress;

    private String description;
}
