package assignment.interview_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Cấu hình Persistence cho Spring Data JPA.
 *
 * <p>
 * Bật tính năng auditing của JPA, cho phép tự động quản lý thông tin về người tạo và người sửa
 * đổi các entity thông qua {@code @CreatedBy} và {@code @LastModifiedBy}.
 * Bean {@link AuditorAware} được đăng ký để Spring Data JPA biết cách lấy thông tin người thực hiện.
 * </p>
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistenceConfig {

    @Bean
    AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

}
