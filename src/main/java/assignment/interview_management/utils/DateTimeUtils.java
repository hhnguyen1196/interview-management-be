package assignment.interview_management.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Utility class cung cấp các hàm hỗ trợ xử lý thời gian và chuyển đổi kiểu dữ liệu thời gian.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtils {

    /**
     * Chuyển đổi {@link LocalDateTime} thành {@link Date} (java.util.Date).
     * Thời gian được chuyển đổi theo múi giờ mặc định của hệ thống.
     *
     * @param localDateTime thời gian kiểu LocalDateTime cần chuyển đổi
     * @return đối tượng Date tương ứng
     */
    public static Date convertToUtilDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
