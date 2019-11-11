package com.hang.server.exceptions;

import com.hang.common.enums.ResultEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * @author hangs.zhang
 * @date 19-8-21 上午11:08
 * *********************
 * function: 参数校验工具类 校验失败则抛出自定义异常
 */
public final class Verify {

    private Verify() {
    }

    public static void verify(boolean expression, ResultEnum resultEnum) {
        if (!expression) {
            throw new ConfigServerException(resultEnum);
        }
    }

    public static void verifyNotNull(ResultEnum resultEnum, Object reference) {
        verify(reference != null, resultEnum);
    }

    public static void verifyNotNull(ResultEnum resultEnum, Object... references) {
        for (Object reference : references) {
            if (reference instanceof String) {
                verify(StringUtils.isNotBlank((String) reference), resultEnum);
            } else {
                verifyNotNull(resultEnum, reference);
            }
        }
    }

}
