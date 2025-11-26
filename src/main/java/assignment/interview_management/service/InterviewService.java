package assignment.interview_management.service;

import assignment.interview_management.dto.InterviewByIdResponse;
import assignment.interview_management.dto.InterviewListResponse;
import assignment.interview_management.dto.InterviewOptionsResponse;
import assignment.interview_management.dto.SaveInterviewRequest;

/**
 * Service xử lý các nghiệp vụ liên quan đến lịch phỏng vấn (Interview).
 * Bao gồm: lấy danh sách, xem chi tiết, tạo mới, cập nhật, xóa và lấy các tùy chọn phỏng vấn.
 */
public interface InterviewService {

    /**
     * Lấy danh sách các lịch phỏng vấn theo từ khóa tìm kiếm và phân trang.
     *
     * @param search từ khóa tìm kiếm (ví dụ: tên ứng viên, vị trí)
     * @param page   số trang (bắt đầu từ 0)
     * @param size   số lượng bản ghi trên mỗi trang
     * @return {@link InterviewListResponse} chứa danh sách lịch phỏng vấn và thông tin phân trang
     */
    InterviewListResponse getAllInterviews(String search, Integer page, Integer size);


    /**
     * Lấy chi tiết một lịch phỏng vấn theo ID.
     *
     * @param id ID của lịch phỏng vấn
     * @return {@link InterviewByIdResponse} chứa thông tin chi tiết của lịch phỏng vấn
     */
    InterviewByIdResponse getInterviewById(Long id);

    /**
     * Tạo mới một lịch phỏng vấn.
     *
     * @param request dữ liệu lịch phỏng vấn cần tạo mới
     */
    void createInterview(SaveInterviewRequest request);

    /**
     * Cập nhật một lịch phỏng vấn hiện có.
     *
     * @param request dữ liệu lịch phỏng vấn cần cập nhật (bao gồm ID)
     */
    void updateInterview(SaveInterviewRequest request);

    /**
     * Lấy các tùy chọn liên quan đến lịch phỏng vấn.
     *
     * @param id ID của lịch phỏng vấn (có thể null để lấy tất cả)
     * @return {@link InterviewOptionsResponse} chứa các tùy chọn phỏng vấn
     */
    InterviewOptionsResponse getInterviewOptions(Long id);

    /**
     * Xóa một lịch phỏng vấn theo ID.
     *
     * @param id ID của lịch phỏng vấn cần xóa
     */
    void deleteInterviewById(Long id);
}
