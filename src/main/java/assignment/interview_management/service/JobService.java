package assignment.interview_management.service;

import assignment.interview_management.dto.JobByIdResponse;
import assignment.interview_management.dto.JobListResponse;
import assignment.interview_management.dto.SaveJobRequest;

public interface JobService {

    JobListResponse getAllJobs(String search, Integer page, Integer size);

    void createJob(SaveJobRequest request);

    void updateJob(SaveJobRequest request);

    JobByIdResponse getJobById(Long id);

    void deleteJobById(Long id);
}
