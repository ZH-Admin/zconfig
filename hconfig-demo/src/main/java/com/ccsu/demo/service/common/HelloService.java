package com.ccsu.demo.service.common;

import com.ccsu.client.annotation.HConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@Slf4j
@Service
public class HelloService {

    @HConfig("hello")
    private Map<String, String> map;

    @PostConstruct
    public void init() {
        printMap();
    }

    public void printMap() {
        log.info("printMap map:{}", map);
    }

}
