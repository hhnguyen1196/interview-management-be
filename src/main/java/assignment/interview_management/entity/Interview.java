package assignment.interview_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Interview extends AuditListener<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
