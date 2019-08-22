package com.hang.server.aop;

import com.hang.server.annotation.StatisticsTime;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.invoke.MethodHandles;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author hangs.zhang
 * @date 2018/7/25
 * *********************
 * function: 性能监控aop，只负责性能监控，不处理异常
 */
@Aspect
@Component
public class StatisticsAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Around("@annotation(statisticsTime)")
    public Object statisticsTime(ProceedingJoinPoint pjp, StatisticsTime statisticsTime) throws Throwable {
        Stopwatch started = Stopwatch.createStarted();
        String monitorName = "default";
        if (Objects.nonNull(statisticsTime)) {
            monitorName = statisticsTime.value();
        } else {
            LOGGER.error("can^t get statistics annotation");
        }

        Object result = null;
        try {
            result = pjp.proceed();
        } finally {
            LOGGER.info("{} cost time:{}", monitorName, started.elapsed(TimeUnit.MILLISECONDS));
        }
        return result;
    }

}
