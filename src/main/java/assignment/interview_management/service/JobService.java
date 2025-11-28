package assignment.interview_management.service;

import assignment.interview_management.dto.JobByIdResponse;
import assignment.interview_management.dto.JobListResponse;
import assignment.interview_management.dto.SaveJobRequest;

/**
 * Service interface định nghĩa các chức năng xử lý nghiệp vụ liên quan đến Job.
 * Bao gồm lấy danh sách job, tạo mới, cập nhật, lấy chi tiết và xóa job.
 */
public interface JobService {

    /**
     * Lấy danh sách job theo từ khóa tìm kiếm và phân trang.
     *
     * @param search từ khóa tìm kiếm (theo tên job)
     * @param page   số trang muốn lấy (0-based)
     * @param size   số bản ghi mỗi trang
     * @return danh sách job kèm thông tin phân trang
     */
    JobListResponse getAllJobs(String search, Integer page, Integer size);

    /**
     * Tạo mới một job dựa trên thông tin từ request.
     *
     * @param request dữ liệu job cần tạo
     */
    void createJob(SaveJobRequest request);

    /**
     * Cập nhật thông tin job dựa trên dữ liệu từ request.
     *
     * @param request dữ liệu job cần cập nhật (cần có ID)
     */
    void updateJob(SaveJobRequest request);

    /**
     * Lấy thông tin chi tiết của một job theo ID.
     *
     * @param id ID của job cần lấy
     * @return thông tin chi tiết job
     */
    JobByIdResponse getJobById(Long id);

    /**
     * Xóa một job theo ID.
     *
     * @param id ID của job cần xóa
     */
    void deleteJobById(Long id);
}
