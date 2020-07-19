package com.github.client.spring.load;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.invoke.MethodHandles;

/**
 * @author hangs.zhang
 * @date 2020/07/19 22:17
 * *****************
 * function:
 */
public class HConfigSchedule {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Resource
    private HotConfigOnFieldProcessor configProcessor;

    @PostConstruct
    public void init() {
        LOGGER.info("config schedule init");
    }

    @Scheduled(cron = "0/30 * * * * *")
    public void pullData() {
        configProcessor.processAll();
    }

}
