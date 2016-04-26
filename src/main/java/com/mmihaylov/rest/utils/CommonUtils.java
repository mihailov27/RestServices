package com.mmihaylov.rest.utils;

import java.util.Collection;

public class CommonUtils {

    public static boolean isNull(Object o) {
        return o == null;
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
