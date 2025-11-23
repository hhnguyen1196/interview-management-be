package assignment.interview_management.controller;

import assignment.interview_management.dto.InterviewByIdResponse;
import assignment.interview_management.dto.InterviewListResponse;
import assignment.interview_management.dto.InterviewOptionsResponse;
import assignment.interview_management.dto.SaveInterviewRequest;
import assignment.interview_management.service.InterviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
public class InterviewController {

    private InterviewService interviewService;

    @GetMapping("/interviews")
    public ResponseEntity<InterviewListResponse> getAllInterviews(@RequestParam("search") String search,
                                                                  @RequestParam("page") Integer page,
                                                                  @RequestParam("size") Integer size) {
        log("GetAllInterviews " + search);
        return ResponseEntity.ok(interviewService.getAllInterviews(search, page, size));
    }

    @PostMapping("/interviews")
    public ResponseEntity<Void> saveInterview(@RequestBody SaveInterviewRequest request) {
        log(request);
        boolean isCreated = request.getId() == null;
        if (isCreated) {
            interviewService.createInterview(request);
        } else {
            interviewService.updateInterview(request);
        }
        return isCreated ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.noContent().build();
    }

    @GetMapping("/interviews/{id}")
    public ResponseEntity<InterviewByIdResponse> getInterviewById(@PathVariable Long id) {
        log(id);
        return ResponseEntity.ok(interviewService.getInterviewById(id));
    }

    @GetMapping("/interviews/options")
    public ResponseEntity<InterviewOptionsResponse> getInterviewOptions(
            @RequestParam(value = "id", required = false) Long id) {
        log("GetInterviewOptions");
        return ResponseEntity.ok(interviewService.getInterviewOptions(id));
    }

    private void log(Object o) {
        log.info("request: {}", o);
    }
}
