package assignment.interview_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Lớp cơ sở cho các entity để tự động quản lý thông tin.
 *
 * <p>
 * Các entity kế thừa lớp này sẽ tự động lưu:
 * <ul>
 *     <li>Người tạo ({@link #createdBy})</li>
 *     <li>Thời gian tạo ({@link #createdDate})</li>
 *     <li>Người cập nhật cuối cùng ({@link #updatedBy})</li>
 *     <li>Thời gian cập nhật cuối cùng ({@link #updatedDate})</li>
 * </ul>
 * </p>
 *
 * <p>
 * <b>Chú ý:</b> Các trường này được Spring Data JPA Auditing tự động điền dựa trên
 * {@link org.springframework.data.domain.AuditorAware} và {@link AuditingEntityListener}.
 * </p>
 *
 * @param <T> kiểu dữ liệu của trường "người thực hiện" (createdBy, updatedBy),
 *            ví dụ String (username), Long (userId), hoặc UUID.
 */

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditListener<T> {

    @CreatedBy
    @Column(name = "created_by")
    private T createdBy;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedBy
    @Column(name = "updated_by")
    private T updatedBy;

    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

}
