package com.github.server.handler;

import com.github.common.pojo.BaseResult;
import com.github.common.enums.ResultEnum;
import com.github.server.exceptions.ConfigServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;
import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@ResponseBody
@ControllerAdvice
public class ServerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @ExceptionHandler(value = Exception.class)
    public BaseResult<?> exceptionHandler(Exception ex, HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((key, value) -> LOGGER.error("error param {}:{}", key, value));
        LOGGER.error("RequestURI {}", request.getRequestURI());
        LOGGER.error("exception", ex);

        if (ex instanceof ConfigServerException) {
            ConfigServerException serverException = (ConfigServerException) ex;
            return BaseResult.error(serverException.getCode(), serverException.getMessage());
        } else {
            // 其他异常
            return BaseResult.error(ResultEnum.SERVER_INNER_ERROR);
        }
    }

}
