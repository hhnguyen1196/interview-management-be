package assignment.interview_management.dto;

public interface GetAllInterviewQuery {

    Long getId();

    String getTitle();

    String getCandidateName();

    String getInterviewerName();

    String getRecruiterName();

    String getSchedule();

    String getStatus();

}
