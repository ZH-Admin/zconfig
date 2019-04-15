package com.hang.server.annotation;

import java.lang.annotation.*;

/**
 * @author hangs.zhang
 * @date 2018/7/25
 * *********************
 * function:
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StatisticsTime {

    // 监控名前缀
    String value() default "";

}
