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

/**
 * Controller quản lý các API liên quan đến lịch phỏng vấn (Interview).
 * Bao gồm các chức năng: xem danh sách, tạo mới, cập nhật, xem chi tiết, xóa và lấy các tùy chọn phỏng vấn.
 */
@Slf4j
@RestController
@AllArgsConstructor
public class InterviewController {

    private InterviewService interviewService;

    /**
     * Lấy danh sách các lịch phỏng vấn theo từ khóa tìm kiếm và phân trang.
     *
     * @param search từ khóa tìm kiếm (ví dụ: tên ứng viên, vị trí)
     * @param page   số trang (bắt đầu từ 0)
     * @param size   số lượng bản ghi trên mỗi trang
     * @return {@link InterviewListResponse} chứa danh sách các lịch phỏng vấn và thông tin phân trang
     */
    @GetMapping("/interviews")
    public ResponseEntity<InterviewListResponse> getAllInterviews(@RequestParam("search") String search,
                                                                  @RequestParam("page") Integer page,
                                                                  @RequestParam("size") Integer size) {
        log("GetAllInterviews " + search);
        return ResponseEntity.ok(interviewService.getAllInterviews(search, page, size));
    }

    /**
     * Tạo mới hoặc cập nhật một lịch phỏng vấn.
     * <ul>
     *     <li>Nếu request.id == null → tạo mới lịch phỏng vấn.</li>
     *     <li>Nếu request.id != null → cập nhật lịch phỏng vấn hiện có.</li>
     * </ul>
     *
     * @param request dữ liệu lịch phỏng vấn (gửi dưới dạng JSON)
     * @return HTTP 201 (Created) nếu tạo mới, HTTP 204 (No Content) nếu cập nhật
     */
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

    /**
     * Lấy thông tin chi tiết của một lịch phỏng vấn theo ID.
     *
     * @param id ID của lịch phỏng vấn
     * @return {@link InterviewByIdResponse} chứa thông tin chi tiết
     */
    @GetMapping("/interviews/{id}")
    public ResponseEntity<InterviewByIdResponse> getInterviewById(@PathVariable Long id) {
        log(id);
        return ResponseEntity.ok(interviewService.getInterviewById(id));
    }

    /**
     * Xóa một lịch phỏng vấn theo ID.
     *
     * @param id ID của lịch phỏng vấn cần xóa
     * @return HTTP 204 (No Content) nếu xóa thành công
     */
    @DeleteMapping("/interviews/{id}")
    public ResponseEntity<Void> deleteInterview(@PathVariable Long id) {
        log(id);
        interviewService.deleteInterviewById(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * Lấy các tùy chọn liên quan đến lịch phỏng vấn.
     * Có thể lấy tất cả hoặc chỉ các tùy chọn liên quan đến một ID nhất định.
     *
     * @param id ID của lịch phỏng vấn (có thể null nếu muốn lấy tất cả tùy chọn)
     * @return {@link InterviewOptionsResponse} chứa các tùy chọn phỏng vấn
     */
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
