package assignment.interview_management.dto;

public interface GetAllAccountQuery {

    Long getId();

    String getUsername();

    String getFullName();

    String getGender();

    String getEmail();

    String getDepartment();

    Boolean getIsActive();
}
