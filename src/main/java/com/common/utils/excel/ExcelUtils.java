package com.common.utils.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.common.utils.excel.handler.DefaultReadHandler;
import com.common.utils.excel.handler.DefaultWriteHandler;
import com.common.utils.excel.handler.DynamicReadHandler;
import com.common.utils.excel.message.ErrorMsg;
import com.common.utils.excel.message.ExcelResult;
import com.common.utils.excel.style.DefaultExcelStyle;
import com.common.utils.excel.style.ExcelStyle;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.common.utils.excel.CommonUtils.*;

/**
 * 表格处理工具类
 *
 * @author nanyanqing
 */
public class ExcelUtils {

    /**
     * 简单的默认导入处理
     *
     * @param file   导入的文件
     * @param source 目标字节码
     * @return 导入的数据
     * @throws IOException io 异常
     */
    public ExcelResult easyReadExcel(MultipartFile file, Class<Object> source, Predicate<Object> checkProperty, boolean isAsync) throws IOException {
        ExcelResult excelResult = readExcel(file, false, source, checkProperty, null, null, isAsync, null);
        return excelResult;
    }

    /**
     * 简单的默认导出
     *
     * @param data     数据
     * @param source   源字节码
     * @param fileName 文件名
     * @throws IOException io异常
     */
    public void easyWriteExcel(List<Object> data, Class<Object> source, String fileName) throws IOException {
        writeExcel(data, null, null, source, fileName, null, null, null);
    }


    /**
     * 动态导入
     * 不支持异步，结果处理使用deal消费处理
     * 不会返回结果，结果集中只有异常信息
     *
     * @param file          文件
     * @param checkProperty 检测
     * @param deal          处理
     * @return 结果
     * @throws IOException Io异常
     */
    public ExcelResult dynamicReadExcel(MultipartFile file, Predicate<Map<String, Object>> checkProperty,
                                        Consumer<Map<String, Object>> deal) throws IOException {
        Map<String, Object> dynamicResult = new HashMap<>();
        ExcelResult excelResult = readExcel(file, true, null, null, null, null, false,
                new DynamicReadHandler(null, dynamicResult, new ErrorMsg(), checkProperty, deal));
        return excelResult;
    }

    /**
     * 动态导出
     *
     * @param data
     * @param head
     * @param fileName
     */
    public void dynamicWriteExcel(List<List<Object>> data, String fileName, List<List<String>> head) throws IOException {
        writeExcel(null, data, null, null, fileName, null, null, head);
    }

    /**
     * 自定义的处理
     *
     * @param file          文件
     * @param source        目标字节码
     * @param checkProperty 自定义校验处理
     * @param dealData      处理解析后的数据:可传分批处理的逻辑
     * @param isAsync       是否异步执行
     * @return 导出结果集
     * @throws IOException Io异常
     */
    public ExcelResult readExcel(MultipartFile file, Boolean isDynamic, Class<Object> source, Predicate<Object> checkProperty, Consumer<List<Object>> dealData
            , BiConsumer<Exception, AnalysisContext> exceptionHandler, boolean isAsync, AnalysisEventListener handler) throws IOException {
        List<Object> result = new ArrayList<>();
        ErrorMsg errorMsg = new ErrorMsg(0, Collections.emptyList());
        ExcelResult excelResult = new ExcelResult();
        checkFileFormat(file);
        if (Objects.isNull(handler)) {
            handler = new DefaultReadHandler(exceptionHandler, source, result, errorMsg, checkProperty, dealData);
        }
        if (isDynamic) {
            EasyExcel.read(file.getInputStream(), handler).doReadAll();
        } else {
            ExcelReaderSheetBuilder readerSheetBuilder = EasyExcel.read(file.getInputStream(), source, handler)
                    // 需要读取批注 默认不读取
                    .extraRead(CellExtraTypeEnum.COMMENT)
                    // 需要读取超链接 默认不读取
                    .extraRead(CellExtraTypeEnum.HYPERLINK)
                    // 需要读取合并单元格信息 默认不读取
                    .extraRead(CellExtraTypeEnum.MERGE).sheet();
            // 是否异步
            if (isAsync) {
                List<Object> readSync = readerSheetBuilder.doReadSync();
                excelResult.setResult(readSync);
            } else {
                readerSheetBuilder.doRead();
                excelResult.setResult(result);
            }
            excelResult.setErrorMsg(errorMsg);
        }
        return excelResult;
    }

    /**
     * 自定义的导出
     *
     * @param data     导出的数据
     * @param source   字节码
     * @param fileName 导出文件名
     * @throws IOException io异常
     */
    public void writeExcel(List<Object> data, List<List<Object>> dynamicData, ExcelTypeEnum typeEnum, Class<Object> source, String fileName,
                           ExcelStyle headerStyle, WriteHandler writeHandler, List<List<String>> head) throws IOException {
        // 获得响应
        ServletOutputStream outputStream = getResponseStream(fileName);
        ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(outputStream, source);
        // 默认类型
        if (!Objects.isNull(typeEnum)) {
            excelWriterBuilder.excelType(typeEnum);
        } else {
            excelWriterBuilder.excelType(ExcelTypeEnum.XLS);
        }
        // 默认样式
        if (!Objects.isNull(headerStyle)) {
            excelWriterBuilder.registerWriteHandler(headerStyle.getStyle());
        } else {
            excelWriterBuilder.registerWriteHandler(new DefaultExcelStyle().getStyle());
        }
        // 默认拦截器
        if (!Objects.isNull(writeHandler)) {
            excelWriterBuilder.registerWriteHandler(writeHandler);
        } else {
            excelWriterBuilder.registerWriteHandler(new DefaultWriteHandler());
        }
        // 动态导入
        if (!CollectionUtils.isEmpty(head) && !CollectionUtils.isEmpty(dynamicData)) {
            excelWriterBuilder.head(head);
            excelWriterBuilder.sheet(fileName).doWrite(dynamicData);
        } else if (!CollectionUtils.isEmpty(data)) {
            excelWriterBuilder.sheet(fileName).doWrite(data);
        } else {
            dealErrorMessage(new ErrorMsg(), -1, "未导入数据");
        }
    }
}
