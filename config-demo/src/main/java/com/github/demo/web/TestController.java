package com.github.demo.web;

import com.github.rpc.ConfigClient;
import com.github.demo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/hello")
    public String sayHello() {
        helloService.printMap();
        return "hello world";
    }

    @GetMapping("/ping")
    public String ping() {
        configClient.getConfig(null);
        return "ok";
    }

}
