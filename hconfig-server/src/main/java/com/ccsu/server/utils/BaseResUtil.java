package com.ccsu.server.utils;

import com.ccsu.server.entity.vo.BaseRes;
import com.ccsu.server.enums.ResultEnum;

/**
 * @author ZhangHang
 * @date 2017-12-28 20:14
 * *****************
 * function:
 */
@SuppressWarnings("all")
public class BaseResUtil {

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
