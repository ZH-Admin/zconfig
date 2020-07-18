package com.github.server.aop;

import com.google.common.collect.Maps;
import com.github.common.utils.JsonUtils;
import com.github.server.annotation.StatisticsTime;
import com.google.common.base.Stopwatch;
import com.github.server.constant.ApplicationConstant;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandles;
import java.util.Map;
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

        Object result;
        printMethodNameAndArgs(pjp);
        try {
            result = pjp.proceed();
        } finally {
            LOGGER.info("{} cost time:{}", monitorName, started.elapsed(TimeUnit.MILLISECONDS));
        }
        return result;
    }

    /**
     * 获取拦截的方法与参数名，以及传入的参数
     */
    private static void printMethodNameAndArgs(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = pjp.getArgs();
        Map<String, Object> map = Maps.newHashMap();
        for (int i = 0; i < parameterNames.length; i++) {
            boolean isContinue = args[i] instanceof HttpServletResponse || args[i] instanceof HttpServletRequest
                    || args[i] instanceof BindingResult || args[i] instanceof MultipartFile;
            if (isContinue) {
                continue;
            }
            map.put(parameterNames[i], args[i]);
        }
        LOGGER.info(String.format(ApplicationConstant.METHOD_INFO_FORMAT, pjp.getTarget().getClass().getName(), pjp.getSignature().getName(), JsonUtils.toJson(map)));
    }

}
