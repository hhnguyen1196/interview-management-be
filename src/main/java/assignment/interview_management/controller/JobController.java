package assignment.interview_management.controller;

import assignment.interview_management.dto.GetJobByIdResponse;
import assignment.interview_management.dto.JobListResponse;
import assignment.interview_management.dto.SaveJobRequest;
import assignment.interview_management.service.JobService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
public class JobController {

    private JobService jobService;

    @GetMapping("/jobs")
    public ResponseEntity<JobListResponse> getAllJobs(@RequestParam("search") String search,
                                                      @RequestParam("page") Integer page,
                                                      @RequestParam("size") Integer size) {
        log("getAllJobs " + search);
        return ResponseEntity.ok(jobService.getAllJobs(search, page, size));
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
        return isCreated ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.noContent().build();
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<GetJobByIdResponse> getJobById(@PathVariable Long id) {
        log(id);
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        log(id);
        jobService.deleteJobById(id);
        return ResponseEntity.noContent().build();
    }

    private void log(Object o) {
        log.info("request: {}", o);
    }
}
