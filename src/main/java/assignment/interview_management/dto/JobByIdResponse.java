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
public class JobByIdResponse {

    private Long id;

    private String title;

    private List<String> skills;

    private String level;

    private String salary;

    private LocalDate startDate;

    private LocalDate endDate;

    private String workingAddress;

    private String description;

    private String status;
}
