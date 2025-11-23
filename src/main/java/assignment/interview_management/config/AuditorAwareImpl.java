package assignment.interview_management.config;

import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * Cung cấp thông tin về "auditor" (người thực hiện thay đổi) cho Spring Data JPA auditing.
 * <p>
 * Class này triển khai {@link AuditorAware} và được Spring quản lý như một bean (@Component).
 * Khi một entity có {@code @CreatedBy} hoặc {@code @LastModifiedBy}, Spring Data sẽ gọi
 * {@link #getCurrentAuditor()} để lấy tên người thực hiện.
 * </p>
 *
 * <p>
 * Username được lấy từ Spring Security context hiện tại.
 * </p>
 */
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @NonNull
    @Override
    public Optional<String> getCurrentAuditor() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return Optional.of(username);
    }

}
