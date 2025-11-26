package assignment.interview_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewByIdResponse {

    private Long id;

    private Long jobId;

    private Long candidateId;

    private Long interviewerId;

    private Long recruiterId;

    private LocalDate scheduleDate;

    private String fromHour;

    private String toHour;

    private String meetingId;

    private String status;
}
