package assignment.interview_management.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;
/**
 * Lớp tiện ích (utility class) hỗ trợ tạo mật khẩu ngẫu nhiên.
 *
 * <p>
 * Lớp được thiết kế dạng private constructor để tránh tạo đối tượng từ bên ngoài,
 * chỉ sử dụng thông qua các phương thức tĩnh (static).
 * </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordUtils {
    /**
     * Tập ký tự được sử dụng để tạo mật khẩu ngẫu nhiên.
     * Bao gồm chữ hoa, chữ thường, chữ số và một số ký tự đặc biệt.
     */
    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
    /**
     * Đối tượng Random dùng để sinh vị trí ngẫu nhiên trong chuỗi CHARACTERS.
     */
    private static final Random RANDOM = new Random();
    /**
     * Tạo một mật khẩu ngẫu nhiên dựa trên độ dài được truyền vào.
     *
     * @param length Độ dài của mật khẩu cần tạo.
     * @return Chuỗi mật khẩu ngẫu nhiên có độ dài tương ứng.
     */
    public static String generatePassword(int length) {
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }
        return password.toString();
    }
}
