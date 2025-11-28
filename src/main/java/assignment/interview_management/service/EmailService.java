package assignment.interview_management.service;

import assignment.interview_management.dto.SendMailRequest;
import org.thymeleaf.context.Context;

/**
 * Service xử lý việc gửi email trong hệ thống.
 * Cho phép gửi email theo template và dữ liệu động đi kèm.
 */
public interface EmailService {

    /**
     * Gửi email dựa trên thông tin request, template và context được cung cấp.
     *
     * @param request  thông tin email cần gửi (người nhận, tiêu đề, nội dung,…)
     * @param template tên template Thymeleaf dùng để render nội dung email
     * @param context  dữ liệu động được truyền vào template
     */
    void sendEmail(SendMailRequest request, String template, Context context);
}
