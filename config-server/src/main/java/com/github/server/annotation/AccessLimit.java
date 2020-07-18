package com.github.server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ZhangHang
 * @date 2018-02-09 12:34
 * *****************
 * function: 访问限制注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {

    /**
     * 多少秒之内
     *
     * @return
     */
    int second() default 10;

    /**
     * 最大访问次数
     *
     * @return
     */
    int maxCount() default 10;

    /**
     * 是否需要登录
     *
     * @return
     */
    boolean needLogin() default true;
}
