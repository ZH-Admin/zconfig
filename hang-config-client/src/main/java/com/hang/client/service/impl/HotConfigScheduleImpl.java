package com.hang.client.service.impl;

import com.hang.client.service.HotConfigOnFieldManager;
import com.hang.client.service.HotConfigSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

    private HotConfigOnFieldManager hotConfigOnFieldManager;

    private ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    public HotConfigScheduleImpl(HotConfigOnFieldManager hotConfigOnFieldManager) {
        this.hotConfigOnFieldManager = hotConfigOnFieldManager;
    }

    @PostConstruct
    public void init() {
        pullData();
    }

    @Override
    public void pullData() {
        scheduledExecutor.scheduleAtFixedRate(() -> hotConfigOnFieldManager.updateAll(),
                0, 30, TimeUnit.SECONDS);
    }

    @Override
    public void pushedData() {
        // TODO: 2018/8/27 消费kafka
    }

}
