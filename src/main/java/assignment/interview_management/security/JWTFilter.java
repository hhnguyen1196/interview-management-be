package assignment.interview_management.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JWTFilter extends GenericFilter {


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
