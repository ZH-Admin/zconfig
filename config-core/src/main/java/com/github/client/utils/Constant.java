package com.github.client.utils;

/**
 * @author hangs.zhang
 * @date 2020/07/19 21:52
 * *****************
 * function:
 */
public final class Constant {

    private Constant() {
    }

    public static final String BASE_DIR = "/tmp/config/" + AppConfig.getName();

    public static final String CONFIG_PATH = BASE_DIR + "/%s";

    public static final String CONFIG_VERSION_ABSOLUTE_PATH = CONFIG_PATH + "/%s";

}
