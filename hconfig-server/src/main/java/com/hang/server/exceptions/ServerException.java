package com.hang.server.exceptions;

import com.hang.common.enums.ResultEnum;
import com.hang.common.exception.BaseException;
import lombok.Getter;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@Getter
public class ServerException extends BaseException {


    public ServerException(ResultEnum resultEnum) {
        super(resultEnum);
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(Integer code) {
        super(code);
    }
}
