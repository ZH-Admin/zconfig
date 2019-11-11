package com.hang.common.entity;

import com.hang.common.enums.ResultEnum;
import lombok.Data;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@Data
public class BaseRes<T> {

    private Integer code;

    private String message;

    private T data;

    public static <T> BaseRes success(T obj) {
        BaseRes<T> baseRes = new BaseRes<>();
        baseRes.setCode(ResultEnum.SUCCESS.getCode());
        baseRes.setMessage(ResultEnum.SUCCESS.getMessage());
        baseRes.setData(obj);
        return baseRes;
    }

    public static <T> BaseRes success() {
        return success(null);
    }

    public static <T> BaseRes error(Integer code, String msg) {
        BaseRes<T> baseRes = new BaseRes<>();
        baseRes.setCode(code);
        baseRes.setMessage(msg);
        baseRes.setData(null);
        return baseRes;
    }

    public static <T> BaseRes error(ResultEnum resultEnum) {
        BaseRes<T> baseRes = new BaseRes<>();
        baseRes.setCode(resultEnum.getCode());
        baseRes.setMessage(resultEnum.getMessage());
        baseRes.setData(null);
        return baseRes;
    }

}
