package com.hang.common.enums;

import lombok.Getter;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@Getter
public enum ResultEnum {

    // common
    SUCCESS("success", 0),
    PARAM_ERROR("param error", -10001),
    SERVER_INNER_ERROR("server inner error", -10002),

    // login
    LOGIN_PASS_NULL("pass is null", -10003),
    LOGIN_VO_NULL("login vo is null", -10004),
    LOGIN_PASS_ERROR("pass error", -10005),
    LOGIN_MOBILE_NOT_EXIST("mobile not exist", -10006),

    // client
    CAN_NOT_CREATE_BASE_DIR("can not create base dir", -20001),
    CAN_NOT_CREATE_CONFIG_DIR("can not create config dir", -20002),
    CAN_NOT_CREATE_CONFIG_FILE("can not create config file", -20003),

    // config
    CONFIG_KEY_IS_EXIST("config key is exist", -10008),

    CONFIG_KEY_IS_NOT_EXIST("config key is not exist", -10009),

    // limit
    ACCESS_LIMIT("not enough permission", -10007);

    private String message;

    private Integer code;

    ResultEnum(String msg, Integer code) {
        this.message = msg;
        this.code = code;
    }

}
