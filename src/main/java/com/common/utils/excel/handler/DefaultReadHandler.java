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
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.common.utils.excel.CommonUtils.dealErrorMessage;
import static com.common.utils.excel.CommonUtils.getIndex2Name;


/**
 * 基础的处理
 *
 * @author nanyanqing
 */
public class DefaultReadHandler extends AnalysisEventListener {
    private static final Integer DEFAULT_EXCEL_NUMBER = 2000;
    private final BiConsumer<Exception, AnalysisContext> exceptionHandler;
    private final Class<Object> source;
    private final List<Object> result;
    private final ErrorMsg errorMsg;
    private final Predicate<Object> checkProperty;
    private final Consumer<List<Object>> deal;

    public DefaultReadHandler(BiConsumer<Exception, AnalysisContext> exceptionHandler, Class<Object> source, List<Object> result, ErrorMsg errorMsg, Predicate<Object> checkProperty, Consumer<List<Object>> deal) {
        this.exceptionHandler = exceptionHandler;
        this.source = source;
        this.result = result;
        this.errorMsg = errorMsg;
        this.checkProperty = checkProperty;
        this.deal = deal;
    }

    /**
     * 解析时的处理，可用于校验属性值
     */
    @Override
    public void invoke(Object data, AnalysisContext context) {
        Field rowNumber = ReflectionUtils.findField(source, "rowNumber");
        if (rowNumber != null) {
            ReflectionUtils.makeAccessible(rowNumber);
            ReflectionUtils.setField(rowNumber, data, context.readRowHolder().getRowIndex());
        }
        // 处理自定义判断逻辑
        if (checkProperty != null) {
            if (checkProperty.test(data)) {
                result.add(data);
            } else {
                dealErrorMessage(errorMsg, context.readRowHolder().getRowIndex(), "未满足约定条件！");
            }
        } else {
            result.add(data);
        }
        // 分批处理防止OOM
        if (result.size() >= DEFAULT_EXCEL_NUMBER && !Objects.isNull(deal)) {
            deal.accept(result);
            result.clear();
        } else if (result.size() >= DEFAULT_EXCEL_NUMBER){
            dealErrorMessage(errorMsg,0,"导入数量过多，且没有分批处理！请减少一次导入量，或者添加分批处理逻辑！");
        }
    }

    /**
     * 解析之后处理转化后的数据，可进行实体的处理
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 用于保证所有数据都会进行处理
        if (!Objects.isNull(deal)){
            deal.accept(result);
        }
        if (CollectionUtils.isEmpty(errorMsg.getMsgInfo())){
            throw new GlobalRuntimeException(ResponseErrorCode.ANALYSIS_ERROR.getCode(), ResponseErrorCode.CHECK_ERROR.getMessage());
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

    /**
     * 根据注解去处理
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
        Map<Integer, List<String>> index2Name = getIndex2Name(source);
        if (index2Name.size() == 1 && index2Name.containsKey(-1)) {
            for (Integer index : index2Name.keySet()) {
                if (CollectionUtils.isEmpty(index2Name.get(index))) {
                    CommonUtils.dealErrorMessage(errorMsg, index, "列标题不可以为空！");
                }
            }
        }
    }
}
