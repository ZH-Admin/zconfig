package com.ccsu.common.utils;

/**
 * @author hangs.zhang
 * @date 2018/8/14
 * *********************
 * function:
 */
public class CommonUtils {

    public static String getRealKey(String prefix, String value) {
        return prefix + "-" + value;
    }

}
