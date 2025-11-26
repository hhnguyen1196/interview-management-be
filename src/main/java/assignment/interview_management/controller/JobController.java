package assignment.interview_management.controller;

import assignment.interview_management.dto.JobByIdResponse;
import assignment.interview_management.dto.JobListResponse;
import assignment.interview_management.dto.SaveJobRequest;
import assignment.interview_management.service.JobService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller quản lý các API liên quan đến Job.
 * Cung cấp các chức năng lấy danh sách job, tạo/cập nhật job,
 * xem chi tiết job theo ID và xóa job.
 */
@Slf4j
@RestController
@AllArgsConstructor
public class JobController {

    private JobService jobService;

    /**
     * API lấy danh sách job theo từ khóa tìm kiếm và phân trang.
     *
     * @param search từ khóa tìm kiếm theo tên job
     * @param page   số trang (bắt đầu từ 0)
     * @param size   số lượng phần tử mỗi trang
     * @return ResponseEntity chứa danh sách job và thông tin phân trang
     */
    @GetMapping("/jobs")
    public ResponseEntity<JobListResponse> getAllJobs(@RequestParam("search") String search,
                                                      @RequestParam("page") Integer page,
                                                      @RequestParam("size") Integer size) {
        log("GetAllJobs " + search);
        return ResponseEntity.ok(jobService.getAllJobs(search, page, size));
    }

    /**
     * API tạo mới hoặc cập nhật job dựa trên dữ liệu từ client.
     * <p>
     * - Nếu request không có ID → tạo mới job  
     * - Nếu request có ID → cập nhật job
     *
     * @param request thông tin job cần lưu
     * @return HTTP 201 nếu tạo mới, HTTP 204 nếu cập nhật thành công
     */
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

    /**
     * API lấy thông tin chi tiết một job theo ID.
     *
     * @param id ID của job cần lấy
     * @return thông tin chi tiết job
     */
    @GetMapping("/jobs/{id}")
    public ResponseEntity<JobByIdResponse> getJobById(@PathVariable Long id) {
        log(id);
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    /**
     * API xóa job theo ID.
     *
     * @param id ID của job cần xóa
     * @return HTTP 204 NO CONTENT khi xóa thành công
     */
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        log(id);
        jobService.deleteJobById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Ghi log thông tin request gửi tới controller.
     *
     * @param o dữ liệu cần log (tham số, request body, ID, ...)
     */
    private void log(Object o) {
        log.info("request: {}", o);
    }
}
