package com.ccsu.server.aop;

import com.ccsu.server.annotation.StatisticsTime;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Slf4j
public class StatisticsAdvice {

    @Around("@annotation(statisticsTime)")
    @ResponseBody
    public Object statisticsTime(ProceedingJoinPoint pjp, StatisticsTime statisticsTime) throws Throwable {
        Stopwatch started = Stopwatch.createStarted();
        String monitorName = "default";
        if (Objects.nonNull(statisticsTime)) {
            monitorName = statisticsTime.value();
        } else {
            log.error("can^t get statistics annotation");
        }

        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Exception e) {
            log.error("{} error", monitorName);
            throw e;
        } finally {
            log.info("{} cost time:{}", monitorName, started.elapsed(TimeUnit.MILLISECONDS));
        }
        return result;
    }

}
