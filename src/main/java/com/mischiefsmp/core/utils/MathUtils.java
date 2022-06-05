package com.mischiefsmp.core.utils;

public class MathUtils {
    public static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
