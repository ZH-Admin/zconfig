package com.github.demo.web;

import com.github.client.model.bo.ConfigRequest;
import com.github.client.rpc.ConfigClient;
import com.github.demo.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private HelloService helloService;

    @Autowired
    private ConfigClient configClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @GetMapping("/hello")
    public String sayHello() {
        helloService.printMap();
        return "hello world";
    }

    @GetMapping("/ping")
    public String ping() {
        LOGGER.info("result {}", configClient.checkUpdate(new ConfigRequest()));
        return "ok";
    }

}
