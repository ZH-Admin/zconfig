package com.hang.client.service.impl;

import com.hang.client.service.HotConfigOnFieldManager;
import com.hang.client.service.HotConfigSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@Service
public class HotConfigScheduleImpl implements HotConfigSchedule {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Resource
    private HotConfigOnFieldManager hotConfigOnFieldManager;

    @PostConstruct
    public void init() {
        LOGGER.info("hot config schedule init");
    }

    @Override
    @Scheduled(cron = "0/30 * * * * *")
    public void pullData() {
        /// scheduledExecutor.scheduleAtFixedRate(() -> hotConfigOnFieldManager.updateAll(), 0, 30, TimeUnit.SECONDS);
        hotConfigOnFieldManager.updateAll();
    }

    @Override
    public void pushedData() {
        // TODO: 2018/8/27 消费kafka
    }

}
