package assignment.interview_management.service;

import assignment.interview_management.dto.GetJobByIdResponse;
import assignment.interview_management.dto.JobListRequest;
import assignment.interview_management.dto.JobListResponse;
import assignment.interview_management.dto.SaveJobRequest;

public interface JobService {

    JobListResponse getAllJobs(JobListRequest request);

    void createJob(SaveJobRequest request);

    void updateJob(SaveJobRequest request);

    GetJobByIdResponse getJobById(Long id);

    void deleteJobById(Long id);
}
