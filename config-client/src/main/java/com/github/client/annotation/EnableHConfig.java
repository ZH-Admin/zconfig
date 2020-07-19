package com.github.client.annotation;

import java.lang.annotation.*;

/**
 * @author hangs.zhang
 * @date 2020/07/19 15:36
 * *****************
 * function:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface EnableHConfig {
}
