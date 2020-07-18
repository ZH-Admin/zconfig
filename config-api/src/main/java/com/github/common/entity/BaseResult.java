package com.github.common.entity;

import com.github.common.enums.ResultEnum;
import lombok.Data;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@Data
public class BaseResult<T> {

    private Integer code;

    private String message;

    private T data;

    public static <T> BaseResult success(T obj) {
        BaseResult<T> baseRes = new BaseResult<>();
        baseRes.setCode(ResultEnum.SUCCESS.getCode());
        baseRes.setMessage(ResultEnum.SUCCESS.getMessage());
        baseRes.setData(obj);
        return baseRes;
    }

    public static <T> BaseResult success() {
        return success(null);
    }

    public static <T> BaseResult error(Integer code, String msg) {
        BaseResult<T> baseRes = new BaseResult<>();
        baseRes.setCode(code);
        baseRes.setMessage(msg);
        baseRes.setData(null);
        return baseRes;
    }

    public static <T> BaseResult error(ResultEnum resultEnum) {
        BaseResult<T> baseRes = new BaseResult<>();
        baseRes.setCode(resultEnum.getCode());
        baseRes.setMessage(resultEnum.getMessage());
        baseRes.setData(null);
        return baseRes;
    }

}
