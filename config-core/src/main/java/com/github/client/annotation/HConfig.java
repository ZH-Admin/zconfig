package com.github.client.annotation;

import java.lang.annotation.*;

/**
 * @author hangs.zhang
 * @date 2018/8/14
 * *********************
 * function:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface HConfig {

    /**
     * 配置名称
     */
    String value();

}
