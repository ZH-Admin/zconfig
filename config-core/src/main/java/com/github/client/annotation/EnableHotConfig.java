package com.github.client.annotation;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

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
@ComponentScan(basePackages = "com.github.client")
@EnableFeignClients(basePackages = "com.github.client.rpc")
public @interface EnableHotConfig {
}
