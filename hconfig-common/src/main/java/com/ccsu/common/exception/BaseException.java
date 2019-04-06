package com.ccsu.common.exception;

import com.ccsu.common.enums.ResultEnum;
import lombok.Getter;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@Getter
public class BaseException extends RuntimeException {

    protected Integer code;

    public BaseException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Integer code) {
        this.code = code;
    }
}
