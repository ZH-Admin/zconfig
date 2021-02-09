package com.github.server.service;

import com.github.server.entity.po.PropertiesPO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * @author zhanghang
 * @date 2021/2/9 4:15 下午
 * *****************
 * function:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigServiceTest {

    @Autowired
    private ConfigService configService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void getConfig() {
        List<PropertiesPO> configs = configService.getConfig("client-demo");
        configs.forEach(config -> LOGGER.info("data {}", config));
    }

    @Test
    public void testGetConfig() {
    }
}