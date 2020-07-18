package com.github.server.exceptions;

import com.hang.common.enums.ResultEnum;
import com.hang.common.exception.ConfigBaseException;
import lombok.Getter;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@Getter
public class ConfigServerException extends ConfigBaseException {

    public ConfigServerException(ResultEnum resultEnum) {
        super(resultEnum);
    }

    public ConfigServerException(String message) {
        super(message);
    }

    public ConfigServerException(Integer code) {
        super(code);
    }

    public ConfigServerException(String message, Throwable e) {
        super(message, e);
    }

}
