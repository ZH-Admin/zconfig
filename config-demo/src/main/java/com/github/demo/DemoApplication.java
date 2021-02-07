package com.github.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author plum-wine
 * 1. 前者为client
 * 2. 后者为项目
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.github.client", "com.github.demo"})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
