package com.github.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@Service
public class HelloService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    // @HConfig("hello")
    private Map<String, String> map;

    @PostConstruct
    public void init() {
        printMap();
    }

    public void printMap() {
        LOGGER.info("printMap map:{}", map);
    }

}
