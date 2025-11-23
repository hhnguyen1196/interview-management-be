package assignment.interview_management.service;

import assignment.interview_management.dto.InterviewByIdResponse;
import assignment.interview_management.dto.InterviewListResponse;
import assignment.interview_management.dto.InterviewOptionsResponse;
import assignment.interview_management.dto.SaveInterviewRequest;

public interface InterviewService {

    InterviewListResponse getAllInterviews(String search, Integer page, Integer size);

    InterviewByIdResponse getInterviewById(Long id);

    void createInterview(SaveInterviewRequest request);

    void updateInterview(SaveInterviewRequest request);

    InterviewOptionsResponse getInterviewOptions(Long id);
}
