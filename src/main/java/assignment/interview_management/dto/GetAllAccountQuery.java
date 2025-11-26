package assignment.interview_management.dto;

public interface GetAllAccountQuery {

    Long getId();

    String getUsername();

    String getFullName();

    String getPhoneNumber();

    String getEmail();

    String getRole();

    Boolean getIsActive();
}
