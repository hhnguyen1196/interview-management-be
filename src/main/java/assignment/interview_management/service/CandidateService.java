package assignment.interview_management.service;

import assignment.interview_management.dto.CandidateByIdResponse;
import assignment.interview_management.dto.CandidateListResponse;
import assignment.interview_management.dto.SaveCandidateRequest;

/**
 * Service cung cấp các nghiệp vụ xử lý liên quan đến ứng viên (Candidate).
 * Bao gồm: tìm kiếm, tạo mới, cập nhật, xem chi tiết và xóa ứng viên.
 */
public interface CandidateService {

    /**
     * Lấy danh sách ứng viên theo từ khóa tìm kiếm và phân trang.
     *
     * @param search từ khóa tìm kiếm (có thể là tên, email,...)
     * @param page   số trang (đánh số từ 0)
     * @param size   số lượng bản ghi trên mỗi trang
     * @return {@link CandidateListResponse} chứa danh sách ứng viên và thông tin phân trang
     */
    CandidateListResponse getAllCandidates(String search, Integer page, Integer size);


    /**
     * Tạo mới một ứng viên.
     *
     * @param request dữ liệu ứng viên cần tạo mới
     */
    void createCandidate(SaveCandidateRequest request);

    /**
     * Cập nhật thông tin một ứng viên.
     *
     * @param request dữ liệu ứng viên cần cập nhật (bao gồm ID)
     */
    void updateCandidate(SaveCandidateRequest request);

    /**
     * Lấy thông tin chi tiết của một ứng viên theo ID.
     *
     * @param id ID của ứng viên
     * @return {@link CandidateByIdResponse} chứa thông tin chi tiết của ứng viên
     */
    CandidateByIdResponse getCandidateById(Long id);


    /**
     * Xóa ứng viên theo ID.
     *
     * @param id ID của ứng viên cần xóa
     */
    void deleteCandidateById(Long id);
}
