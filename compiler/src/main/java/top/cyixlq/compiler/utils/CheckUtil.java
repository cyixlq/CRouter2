package top.cyixlq.compiler.utils;

import java.util.Map;
import java.util.Set;

public class CheckUtil {

    public static boolean isNotEmpty(Map map) {
        return map != null && !map.isEmpty();
    }

    public static boolean isNotEmpty(CharSequence str) {
        return str != null && str.length() > 0;
    }

    public static boolean isNotEmpty(Set set) {
        return set != null && !set.isEmpty();
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

}
