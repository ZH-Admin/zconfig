package com.github.server.constant;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
public final class ApplicationConstant {

    private ApplicationConstant() {
    }

    /**
     * token的保存时间为2天
     */
    public static final int TOKEN_EXPIRE = 60 * 60 * 24 * 2;

    public static final String METHOD_INFO_FORMAT = "###class:%s###method:%s###args:%s###";

}
