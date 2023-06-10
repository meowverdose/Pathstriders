package org.nekoverse.seele.utils;

/**
 * https://stackoverflow.com/questions/28332924/case-insensitive-matching-of-a-string-to-a-java-enum
 */
public class EnumUtil {

    public static <T extends Enum<T>> T exists(Class<T> type, String name) {
        for (T each : type.getEnumConstants()) {
            if (each.name().compareToIgnoreCase(name) == 0) {
                return each;
            }
        }
        return null;
    }
}