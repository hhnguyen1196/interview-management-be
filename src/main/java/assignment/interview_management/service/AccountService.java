package assignment.interview_management.service;

import assignment.interview_management.dto.AccountByIdResponse;
import assignment.interview_management.dto.AccountInfoResponse;
import assignment.interview_management.dto.AccountListResponse;
import assignment.interview_management.dto.SaveAccountRequest;

/**
 * Service định nghĩa các nghiệp vụ liên quan đến quản lý tài khoản người dùng.
 *
 * <p>
 * Interface này đóng vai trò như một "hợp đồng" (contract), mô tả các chức năng
 * mà hệ thống phải hỗ trợ cho việc quản lý tài khoản, bao gồm:
 * tìm kiếm – phân trang, tạo mới, cập nhật và lấy thông tin chi tiết tài khoản.
 * </p>
 */
public interface AccountService {
    /**
     * Lấy danh sách tài khoản theo từ khóa tìm kiếm và phân trang.
     *
     * @param search Từ khóa tìm kiếm (username, email, tên đầy đủ,...)
     * @param page   Số trang (0-based)
     * @param size   Số lượng phần tử mỗi trang
     * @return Danh sách tài khoản và tổng số lượng bản ghi tìm được
     */
    AccountListResponse getAllAccounts(String search, Integer page, Integer size);

    /**
     * Tạo mới một tài khoản trong hệ thống.
     *
     * @param request Thông tin tài khoản cần tạo mới
     */
    void createAccount(SaveAccountRequest request);

    /**
     * Cập nhật thông tin tài khoản hiện có.
     *
     * @param request Thông tin tài khoản cần cập nhật (bao gồm ID)
     */
    void updateAccount(SaveAccountRequest request);

    /**
     * Lấy thông tin chi tiết của tài khoản theo ID.
     *
     * @param id ID của tài khoản
     * @return Thông tin chi tiết của tài khoản tương ứng
     */
    AccountByIdResponse getAccountById(Long id);

    AccountInfoResponse getAccountInfo();
}
