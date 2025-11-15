package assignment.interview_management.service;

import assignment.interview_management.dto.CandidateByIdResponse;
import assignment.interview_management.dto.CandidateListResponse;
import assignment.interview_management.dto.SaveCandidateRequest;

public interface CandidateService {

    CandidateListResponse getAllCandidates(String search, Integer page, Integer size);

    void createCandidate(SaveCandidateRequest request);

    void updateCandidate(SaveCandidateRequest request);

    CandidateByIdResponse getCandidateById(Long id);

    void deleteCandidateById(Long id);
}
