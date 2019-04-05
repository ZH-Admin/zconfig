package com.ccsu.server.redis.key;

/**
 * @author ZhangHang
 * @date 2018-01-29 22:07
 * *****************
 * function: 模板方法模式
 *
 * interface -> abstract -> class
 */
public interface KeyPrefix {

    /**
     * 过期时间设置
     *
     * @return
     */
    int expireSeconds();

    /**
     * key的前缀
     *
     * @return
     */
    String getPrefix();

}
