package com.github.client.spring.load;

import com.github.client.utils.ConfigServerHttpClient;
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
    private HotConfigOnFieldLoader fieldLoader;

    @PostConstruct
    public void init() {
        LOGGER.info("config schedule init");
        scheduledExecutorService.schedule(() -> {
            while (true) {
                try {
                    // long pull, 长轮询
                    LOGGER.info("start pull");
                    if (ConfigServerHttpClient.checkConfigUpdate()) {
                        fieldLoader.processAll();
                    }
                    LOGGER.info("end pull");
                } catch (Exception e) {
                    LOGGER.error("long pull error", e);
                }
            }
        }, 60, TimeUnit.SECONDS);
    }

}
