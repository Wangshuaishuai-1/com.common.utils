package com.common.utils.annotation.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * 功能测试的时间处理
 *
 * @author nanyanqing
 */
public class TimePerformanceProcessor extends AbstractProcessor {
    private Messager messager;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return false;
    }



}
