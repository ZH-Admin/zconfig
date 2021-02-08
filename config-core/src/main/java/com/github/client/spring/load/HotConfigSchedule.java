package com.github.client.spring.load;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.*;

/**
 * @author hangs.zhang
 * @date 2020/07/19 22:17
 * *****************
 * function:
 */
public class HotConfigSchedule {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    @Resource
    private HotConfigOnFieldLoader configProcessor;

    @PostConstruct
    public void init() {
        LOGGER.info("config schedule init");
        scheduledExecutorService.schedule(() -> {
            LOGGER.info("request new config");
            configProcessor.processAll();
        }, 60, TimeUnit.MILLISECONDS);
    }

}
