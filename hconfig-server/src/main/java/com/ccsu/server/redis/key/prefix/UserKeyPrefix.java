package com.ccsu.server.redis.key.prefix;

import com.ccsu.server.redis.key.BasePrefix;

import static com.ccsu.server.config.ApplicationConfig.TOKEN_EXPIRE;

/**
 * @author ZhangHang
 * @date 2018-01-29 22:15
 * *****************
 * function:
 */
public class UserKeyPrefix extends BasePrefix {

    public UserKeyPrefix(String prefix) {
        super(prefix);
    }

    public UserKeyPrefix(String prefix, Integer expireSeconds) {
        super(expireSeconds, prefix);
    }

    public static UserKeyPrefix token = new UserKeyPrefix("token", TOKEN_EXPIRE);

}
