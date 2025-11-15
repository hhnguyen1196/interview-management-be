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
public class CandidateListResponse {

    private List<Candidate> candidateList;

    private Integer totalElements;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Candidate {

        private Long id;

        private String fullName;

        private String email;

        private String phoneNumber;

        private String position;

        private String status;
    }
}
