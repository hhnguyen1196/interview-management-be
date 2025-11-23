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
public class InterviewListResponse {

    private List<Interview> interviewList;

    private Integer totalElements;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Interview {

        private Long id;

        private String title;

        private String candidateName;

        private String interviewerName;

        private String recruiterName;

        private String schedule;

        private String status;
    }
}
