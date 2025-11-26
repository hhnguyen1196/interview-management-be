package assignment.interview_management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;


/**
 * Cấu hình Spring để hỗ trợ thực thi bất đồng bộ (asynchronous).
 *
 * <p>
 * Khi class này được đăng ký, các method được đánh dấu {@link org.springframework.scheduling.annotation.Async}
 * sẽ được Spring thực thi trong thread riêng, không làm chặn thread chính.
 * Điều này hữu ích cho các tác vụ tốn thời gian như gửi email, gọi API ngoài, xử lý file,...
 * </p>
 */
@EnableAsync
@Configuration
public class AsyncConfig {
}
