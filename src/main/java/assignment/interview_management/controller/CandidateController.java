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

/**
 * Controller cung cấp các API để quản lý thông tin ứng viên (Candidate).
 * Bao gồm các chức năng: lấy danh sách ứng viên, tạo mới, cập nhật, xem chi tiết và xóa ứng viên.
 */
@Slf4j
@RestController
@AllArgsConstructor
public class CandidateController {

    private CandidateService candidateService;

    /**
     * API lấy danh sách ứng viên theo từ khóa tìm kiếm và phân trang.
     *
     * @param search từ khóa tìm kiếm (tên, email,...)
     * @param page   số trang (đánh số từ 0)
     * @param size   số lượng bản ghi trên mỗi trang
     * @return {@link CandidateListResponse} chứa danh sách ứng viên và thông tin phân trang
     */
    @GetMapping("/candidates")
    public ResponseEntity<CandidateListResponse> getAllCandidates(@RequestParam("search") String search,
                                                                  @RequestParam("page") Integer page,
                                                                  @RequestParam("size") Integer size) {
        log("GetAllCandidates " + search);
        return ResponseEntity.ok(candidateService.getAllCandidates(search, page, size));
    }

    /**
     * API tạo mới hoặc cập nhật ứng viên.
     * <ul>
     *     <li>Nếu request.id == null → tạo mới.</li>
     *     <li>Nếu request.id != null → cập nhật ứng viên.</li>
     * </ul>
     *
     * @param request dữ liệu ứng viên gửi từ client (form-data hoặc model attribute)
     * @return HTTP 201 (Created) nếu tạo mới, HTTP 204 (No Content) nếu cập nhật
     */
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

    /**
     * API lấy thông tin chi tiết của một ứng viên theo ID.
     *
     * @param id ID của ứng viên
     * @return {@link CandidateByIdResponse} chứa thông tin chi tiết của ứng viên
     */
    @GetMapping("/candidates/{id}")
    public ResponseEntity<CandidateByIdResponse> getCandidateById(@PathVariable Long id) {
        log(id);
        return ResponseEntity.ok(candidateService.getCandidateById(id));
    }

    /**
     * API xóa ứng viên theo ID.
     *
     * @param id ID của ứng viên cần xóa
     * @return HTTP 204 (No Content) nếu xóa thành công
     */
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
