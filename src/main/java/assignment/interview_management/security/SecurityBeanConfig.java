package assignment.interview_management.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Cấu hình các bean cần thiết cho Spring Security.
 *
 * <p>
 * Class này định nghĩa các bean sau:
 * <ul>
 *     <li>{@link PasswordEncoder}: sử dụng {@link BCryptPasswordEncoder} để mã hóa mật khẩu.</li>
 *     <li>{@link AuthenticationManager}: bean quản lý việc xác thực người dùng.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Các bean này được sử dụng trong toàn bộ ứng dụng để đảm bảo an toàn khi lưu trữ mật khẩu
 * và xử lý xác thực.
 * </p>
 */
@Configuration
@AllArgsConstructor
public class SecurityBeanConfig {

    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
