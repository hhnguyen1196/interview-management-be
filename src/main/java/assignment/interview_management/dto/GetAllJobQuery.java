package assignment.interview_management.dto;

import java.time.LocalDate;

public interface GetAllJobQuery {

    Long getId();

    String getTitle();

    String getLevel();

    String getSalary();

    LocalDate getStartDate();

    LocalDate getEndDate();

    String getWorkingAddress();

    String getDescription();

    String getStatus();
}
