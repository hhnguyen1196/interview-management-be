package assignment.interview_management.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnumUtils {

    public static <T> boolean isInvalid(String name, Class<T> clazz) {
        return Arrays.stream(clazz.getEnumConstants()).noneMatch(e -> e.toString().equals(name));
    }

}
