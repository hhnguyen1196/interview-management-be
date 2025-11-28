package assignment.interview_management.service;

import org.springframework.core.io.Resource;

/**
 * Service cung cấp chức năng tải xuống (download) tài nguyên từ hệ thống.
 * Dùng để xử lý logic lấy file theo tên và trả về dưới dạng {@link Resource}.
 */
public interface ApiDownloadService {


    /**
     * Tải xuống file theo tên file được cung cấp.
     *
     * @param filename tên file cần tải (ví dụ: "cv.pdf")
     * @return {@link Resource} đại diện cho file được tải về
     */
    Resource download(String filename);
}
