package com.common.utils.exception;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * 自定义异常处理器
 *
 * @author nanyanqing
 */
@ControllerAdvice
public class ControllerAdviceProcessor {
    private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(GlobalRuntimeException.class)
    @ResponseBody
    public Map<String, Object> handleException(GlobalRuntimeException exception) {
        Map<String, Object> map = new ConcurrentHashMap<>();
        map.put("status", exception.getCode());
        map.put("message", exception.getMessage());
        logger.info(exception.getCode() + ":" + exception.getMessage());
        return map;
    }
}