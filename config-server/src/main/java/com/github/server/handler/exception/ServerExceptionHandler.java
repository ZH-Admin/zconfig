package com.github.server.handler.exception;

import com.github.common.entity.BaseResult;
import com.github.common.enums.ResultEnum;
import com.github.server.exceptions.ConfigServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@ResponseBody
@ControllerAdvice
@Slf4j
public class ServerExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public BaseResult exceptionHandler(Exception ex, HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        // 打印发生异常的请求参数
        parameterMap.forEach((key, value) -> log.error("error param {}:{}", key, value));
        // 打印请求的路径，可以根据这个路径去在监控中mark
        log.error("RequestURI {}", request.getRequestURI());
        // 打印异常堆栈
        log.error("exception", ex);

        // 自定义的感兴趣的异常
        if (ex instanceof ConfigServerException) {
            ConfigServerException serverException = (ConfigServerException) ex;
            return BaseResult.error(serverException.getCode(), serverException.getMessage());
        } else {
            // 其他异常
            return BaseResult.error(ResultEnum.SERVER_INNER_ERROR);
        }
    }

}
