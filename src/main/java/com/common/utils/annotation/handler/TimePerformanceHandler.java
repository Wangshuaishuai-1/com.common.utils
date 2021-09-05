package com.common.utils.annotation.handler;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 时间注解处理器
 *
 * @author nanyanqing
 */
@Aspect
@Component
public class TimePerformanceHandler {

    /**
     * 功能测试
     */
    @Around("")
    public void timeStampPrinting() {
        // 方法执行之前
        long startTime = System.currentTimeMillis();

        // 方法执行之后
        long endTime = System.currentTimeMillis();

        // 性能答应
        System.out.println("方法所用时间：" + (endTime - startTime));
    }

    /**
     * @return
     */
    @Before("")
    public long beforeMethodTime() {
        return System.currentTimeMillis();
    }

    /**
     * @return
     */
    @After("")
    public long afterMethodTime() {
        return System.currentTimeMillis();
    }
}
