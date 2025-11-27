package assignment.interview_management.service;

/**
 * Service xử lý các tác vụ bất đồng bộ trong hệ thống.
 * Interface này định nghĩa các hành động chạy nền không chặn luồng chính,
 * ví dụ như xóa file hoặc các công việc tốn thời gian khác.
 */
public interface AsyncService {

    /**
     * Xóa một file theo tên file được truyền vào.
     * Thường được triển khai để chạy dưới dạng bất đồng bộ (async),
     * nhằm tránh chặn request chính.
     *
     * @param filename tên file cần xóa
     */
    void deleteFile(String filename);
}
