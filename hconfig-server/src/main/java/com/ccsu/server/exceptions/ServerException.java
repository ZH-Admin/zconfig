package com.ccsu.server.exceptions;

import com.ccsu.server.enums.ResultEnum;
import lombok.Getter;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@Getter
public class ServerException extends RuntimeException {

    private Integer code;

    public ServerException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(Integer code) {
        this.code = code;
    }
}
