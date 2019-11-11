package com.hang.server.utils;

/**
 * @author hangs.zhang
 * @date 2019/4/6
 * *****************
 * function:
 */
public class KeyUtils {

    private KeyUtils() {
        throw new RuntimeException("KeyUtils not allow structure");
    }

    public static String getRealKey(String appName, String hConfigKey) {
        return appName + "-" + hConfigKey;
    }


}
