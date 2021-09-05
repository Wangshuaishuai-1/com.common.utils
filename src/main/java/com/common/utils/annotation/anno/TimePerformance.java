package com.common.utils.annotation.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 测试性能的注解，用于打印时间
 *
 * @author nanyanqing
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimePerformance {

    /**
     * 打印的前缀：默认为方法名
     *
     * @return
     */
    public String message() default "method Name";

    /**
     * 是否打印异常
     *
     * @return
     */
    public boolean exception() default true;

    /**
     * 是否打印方法名
     *
     * @return
     */
    public boolean method() default true;

    /**
     * 打印位置
     *
     * @return
     */
    public String path() default "className";
}
