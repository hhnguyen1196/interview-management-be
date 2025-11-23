package assignment.interview_management.controller;

import assignment.interview_management.dto.CandidateByIdResponse;
import assignment.interview_management.dto.CandidateListResponse;
import assignment.interview_management.dto.SaveCandidateRequest;
import assignment.interview_management.service.CandidateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
public class CandidateController {

    private CandidateService candidateService;

    @GetMapping("/candidates")
    public ResponseEntity<CandidateListResponse> getAllCandidates(@RequestParam("search") String search,
                                                                  @RequestParam("page") Integer page,
                                                                  @RequestParam("size") Integer size) {
        log("GetAllCandidates " + search);
        return ResponseEntity.ok(candidateService.getAllCandidates(search, page, size));
    }

    @PostMapping("/candidates")
    public ResponseEntity<Void> saveCandidate(@ModelAttribute SaveCandidateRequest request) {
        log(request);
        boolean isCreated = request.getId() == null;
        if (isCreated) {
            candidateService.createCandidate(request);
        } else {
            candidateService.updateCandidate(request);
        }
        return isCreated ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.noContent().build();
    }

    @GetMapping("/candidates/{id}")
    public ResponseEntity<CandidateByIdResponse> getCandidateById(@PathVariable Long id) {
        log(id);
        return ResponseEntity.ok(candidateService.getCandidateById(id));
    }

    @DeleteMapping("/candidates/{id}")
    public ResponseEntity<Void> deleteCandidateById(@PathVariable Long id) {
        log(id);
        candidateService.deleteCandidateById(id);
        return ResponseEntity.noContent().build();
    }

    private void log(Object o) {
        log.info("request: {}", o);
    }
}
