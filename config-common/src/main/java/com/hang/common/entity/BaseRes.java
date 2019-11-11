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

    public static BaseRes success(Object obj) {
        BaseRes baseRes = new BaseRes();
        baseRes.setCode(ResultEnum.SUCCESS.getCode());
        baseRes.setMessage(ResultEnum.SUCCESS.getMessage());
        baseRes.setData(obj);
        return baseRes;
    }

    public static BaseRes success() {
        return success(null);
    }

    public static BaseRes error(Integer code, String msg) {
        BaseRes baseRes = new BaseRes();
        baseRes.setCode(code);
        baseRes.setMessage(msg);
        baseRes.setData(null);
        return baseRes;
    }

    public static BaseRes error(ResultEnum resultEnum) {
        BaseRes baseRes = new BaseRes();
        baseRes.setCode(resultEnum.getCode());
        baseRes.setMessage(resultEnum.getMessage());
        baseRes.setData(null);
        return baseRes;
    }

}
