package com.github.client.spring.load;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
@Component
public class HotConfigSchedule {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    @Resource
    private HotConfigOnFieldLoader configProcessor;

    @PostConstruct
    public void init() {
        LOGGER.info("config schedule init");
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                LOGGER.info("request new config");
                configProcessor.processAll();
            } catch (Exception e) {
                LOGGER.error("request new config error", e);
            }
        }, 60, 10, TimeUnit.SECONDS);
    }

}
