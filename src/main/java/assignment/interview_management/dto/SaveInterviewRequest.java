package assignment.interview_management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
public class SaveInterviewRequest {

    private Long id;

    private Long jobId;

    private Long candidateId;

    private Long interviewerId;

    private Long recruiterId;

    private LocalDate scheduleDate;

    private LocalTime fromHour;

    private LocalTime toHour;

    private String meetingId;
}
