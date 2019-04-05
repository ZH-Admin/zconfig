package com.ccsu.server.redis.key;

/**
 * @author ZhangHang
 * @date 2018-01-29 22:09
 * *****************
 * function:
 */
public abstract class BasePrefix implements KeyPrefix {

    private Integer expireSeconds;

    private String prefix;

    public BasePrefix(String prefix) {
        // 默认0永不过期
        this(0, prefix);
    }

    public BasePrefix(Integer expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
