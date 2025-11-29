package assignment.interview_management.security;

import assignment.interview_management.enums.AccountRole;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

/**
 * Cấu hình quyền truy cập (authorization) cho các endpoint.
 *
 * <p>
 * Class này định nghĩa các quy tắc cho các request HTTP:
 * </p>
 *
 * <p>
 * Class này được sử dụng trong {@link FilterChainConfiguration} để tách riêng phần cấu hình quyền
 * truy cập, giúp code dễ đọc và bảo trì.
 * </p>
 */
@Component
public class PermissionConfiguration {

    public void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/api/download").permitAll()
                .requestMatchers("/accounts/info")
                .hasAnyAuthority(AccountRole.ADMIN.name(), AccountRole.INTERVIEWER.name(), AccountRole.RECRUITER.name())
                .requestMatchers("/accounts/**", "/auth/forgot-password")
                .hasAuthority(AccountRole.ADMIN.name())
                .anyRequest().authenticated());
    }
}
