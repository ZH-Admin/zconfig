package com.github.client.utils;

import com.google.common.base.Joiner;

/**
 * @author hangs.zhang
 * @date 2019/4/6
 * *****************
 * function:
 */
public final class KeyUtils {
    /**
     * KEY分隔符
     */
    private static final char KEY_SAPERATOR = '-';

    private static final Joiner JOINER = Joiner.on(KEY_SAPERATOR);

    private KeyUtils() {
        // 禁用构造函数
    }

    public static String getKey(String... inputs) {
        return JOINER.join(inputs);
    }

}
