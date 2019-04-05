package com.ccsu.demo.web;

import com.ccsu.demo.service.common.HelloService;
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
@RequestMapping("/hello")
public class HelloController {

    private HelloService helloService;

    @Autowired
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/say")
    public String sayHello() {
        helloService.printMap();
        return "hello world";
    }

}
