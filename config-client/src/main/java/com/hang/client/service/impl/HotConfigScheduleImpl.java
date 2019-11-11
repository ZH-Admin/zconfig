package com.hang.client.service.impl;

import com.hang.client.service.HotConfigProcessor;
import com.hang.client.service.HotConfigScheduleProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.invoke.MethodHandles;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@Service
public class HotConfigScheduleImpl implements HotConfigScheduleProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Resource
    private HotConfigProcessor hotConfigProcessor;

    @PostConstruct
    public void init() {
        LOGGER.info("hot config schedule init");
    }

    @Override
    @Scheduled(cron = "0/30 * * * * *")
    public void pullData() {
        /// scheduledExecutor.scheduleAtFixedRate(() -> hotConfigOnFieldManager.updateAll(), 0, 30, TimeUnit.SECONDS);
        hotConfigProcessor.updateAll();
    }

    @Override
    public void pushedData() {
        // TODO: 2018/8/27 消费kafka
    }

}
