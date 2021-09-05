package com.common.utils.excel.handler;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.common.utils.excel.CommonUtils;
import com.common.utils.excel.message.ErrorMsg;
import com.common.utils.exception.GlobalRuntimeException;
import com.common.utils.exception.ResponseErrorCode;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.common.utils.excel.CommonUtils.dealErrorMessage;

/**
 * @author nanyanqing
 */
public class DynamicReadHandler extends AnalysisEventListener<Map<String, Object>> {
    private static final Integer DEFAULT_EXCEL_NUMBER = 2000;
    private final BiConsumer<Exception, AnalysisContext> exceptionHandler;
    private final Map<String, Object> result;
    private final ErrorMsg errorMsg;
    private final Predicate<Map<String, Object>> checkProperty;
    private final Consumer<Map<String, Object>> deal;

    public DynamicReadHandler(BiConsumer<Exception, AnalysisContext> exceptionHandler, Map<String, Object> result, ErrorMsg errorMsg, Predicate<Map<String, Object>> checkProperty, Consumer<Map<String, Object>> deal) {
        this.exceptionHandler = exceptionHandler;
        this.result = result;
        this.errorMsg = errorMsg;
        this.checkProperty = checkProperty;
        this.deal = deal;
    }

    @Override
    public void invoke(Map<String, Object> data, AnalysisContext context) {
        if (!CollectionUtils.isEmpty(data)) {
            for (String name : data.keySet()) {
                // 处理自定义判断逻辑
                if (checkProperty != null) {
                    if (checkProperty.test(data)) {
                        result.put(name, data.get(name));
                    } else {
                        dealErrorMessage(errorMsg, context.readRowHolder().getRowIndex(), "未满足约定条件！");
                    }
                } else {
                    result.put(name, data.get(name));
                }
                // 分批处理防止OOM
                if (result.size() >= DEFAULT_EXCEL_NUMBER && !Objects.isNull(deal)) {
                    deal.accept(result);
                    result.clear();
                } else if (result.size() >= DEFAULT_EXCEL_NUMBER) {
                    dealErrorMessage(errorMsg, 0, "导入数量过多，且没有分批处理！请减少一次导入量，或者添加分批处理逻辑！");
                }
            }
        }
    }

    /**
     * 遇到异常的处理
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        super.onException(exception, context);
        if (!Objects.isNull(exceptionHandler)) {
            exceptionHandler.accept(exception, context);
        }
        dealErrorMessage(errorMsg, context.readRowHolder().getRowIndex(), exception.getMessage());
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 用于保证所有数据都会进行处理
        if (!Objects.isNull(deal)) {
            deal.accept(result);
        }
        if (CollectionUtils.isEmpty(errorMsg.getMsgInfo())) {
            throw new GlobalRuntimeException(ResponseErrorCode.ANALYSIS_ERROR.getCode(), ResponseErrorCode.CHECK_ERROR.getMessage());
        }
    }
}
