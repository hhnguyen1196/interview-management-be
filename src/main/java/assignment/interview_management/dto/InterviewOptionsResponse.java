package assignment.interview_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewOptionsResponse {

    List<Job> jobList;

    List<Candidate> candidateList;

    List<Interviewer> interviewerList;

    List<Recruiter> recruiterList;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Job {

        private Long id;

        private String title;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Candidate {

        private Long id;

        private String name;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Interviewer {

        private Long id;

        private String name;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Recruiter {

        private Long id;

        private String name;
    }
}
