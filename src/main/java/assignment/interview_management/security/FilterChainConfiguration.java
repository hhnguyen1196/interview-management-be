package assignment.interview_management.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Cấu hình chuỗi filter bảo mật cho Spring Security.
 *
 * <p>
 * Class này định nghĩa:
 * <ul>
 *     <li>Tắt CSRF vì ứng dụng sử dụng JWT.</li>
 *     <li>Cấu hình CORS cho các phương thức GET, POST, DELETE.</li>
 *     <li>Thêm JWT filter trước {@link UsernamePasswordAuthenticationFilter} để xác thực token.</li>
 *     <li>Thiết lập handler cho lỗi 401 (chưa xác thực) và 403 (không có quyền).</li>
 *     <li>Delegation cho {@link PermissionConfiguration} để cấu hình quyền truy cập theo route.</li>
 * </ul>
 * </p>
 */
@Configuration
@AllArgsConstructor
public class FilterChainConfiguration {

    private JWTFilter jwtFilter;

    private AuthenticationEntryPointHandler authenticationEntryPointHandler;

    private AccessDeniedErrorHandler accessDeniedErrorHandler;

    private PermissionConfiguration permissionConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPointHandler)
                        .accessDeniedHandler(accessDeniedErrorHandler));
        permissionConfiguration.configure(http);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedMethods(List.of("GET", "POST", "DELETE"));
        config.setAllowedOriginPatterns(List.of(CorsConfiguration.ALL));
        config.setAllowedHeaders(List.of(CorsConfiguration.ALL));
        config.setAllowCredentials(false);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
