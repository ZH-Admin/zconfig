package com.github.server.utils;

/**
 * @author hangs.zhang
 * @date 2019/4/6
 * *****************
 * function:
 */
public final class KeyUtils {

    private KeyUtils() {
    }

    public static String getRealKey(String appName, String hConfigKey) {
        return appName + "-" + hConfigKey;
    }

}
