package assignment.interview_management.controller;

import assignment.interview_management.dto.GetJobByIdResponse;
import assignment.interview_management.dto.JobListRequest;
import assignment.interview_management.dto.JobListResponse;
import assignment.interview_management.dto.SaveJobRequest;
import assignment.interview_management.service.JobService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
public class JobController {

    private JobService jobService;

    @GetMapping("/jobs")
    public ResponseEntity<JobListResponse> getAllJobs(@RequestBody JobListRequest request) {
        log(request);
        return ResponseEntity.ok(jobService.getAllJobs(request));
    }

    @PostMapping("/jobs")
    public ResponseEntity<Void> saveJob(@RequestBody SaveJobRequest request) {
        log(request);
        boolean isCreated = request.getId() == null;
        if (isCreated) {
            jobService.createJob(request);
        } else {
            jobService.updateJob(request);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<GetJobByIdResponse> getJobById(@PathVariable Long id) {
        log(id);
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @PostMapping("/jobs/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        log(id);
        jobService.deleteJobById(id);
        return ResponseEntity.ok().build();
    }

    private void log(Object o) {
        log.info("request: {}", o);
    }
}
