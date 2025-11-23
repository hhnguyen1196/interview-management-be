package assignment.interview_management.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Lọc JWT để xác thực người dùng trong Spring Security.
 *
 * <p>
 * Class này implement {@link Filter} của Servlet API. Nó sẽ đọc header Authorization từ request,
 * kiểm tra JWT token thông qua {@link TokenProvider}, và nếu hợp lệ, thiết lập
 * {@link Authentication} vào {@link SecurityContextHolder}.
 * </p>
 *
 * <p>
 * Sau khi filter này chạy, các filter và controller phía sau có thể truy cập thông tin người dùng
 * đã xác thực.
 * </p>
 */
@Component
@AllArgsConstructor
public class JWTFilter implements Filter {


    private TokenProvider tokenProvider;

    private static final String AUTHORIZATION = "Authorization";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filter)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Authentication authentication = tokenProvider.getAuthentication(request.getHeader(AUTHORIZATION));
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filter.doFilter(servletRequest, servletResponse);
    }
}
