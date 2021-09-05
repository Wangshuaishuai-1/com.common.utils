package com.common.utils.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.common.utils.excel.message.ErrorMsg;
import com.common.utils.exception.GlobalRuntimeException;
import com.common.utils.exception.ResponseErrorCode;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author nanyanqing
 */
public class CommonUtils {

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";
    /**
     * 处理注解值
     *
     * @param project 导入实体
     * @param <T>     范型
     * @return 文件的标头
     */
    public static  <T> Map<Integer, List<String>> getIndex2Name(Class<T> project) {
        Map<Integer, List<String>> result = new HashMap<>();
        ReflectionUtils.doWithFields(project, field -> {
            ReflectionUtils.makeAccessible(field);
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if (excelProperty != null) {
                int index = excelProperty.index();
                String[] value = excelProperty.value();
                result.computeIfAbsent(index, key -> new ArrayList<>()).addAll(Arrays.asList(value));
            }
        });
        return result;
    }

    /**
     * 定义公共的输出流
     *
     * @param fileName 文件名
     * @return ServletOutputStream 输出流
     * @throws IOException 异常
     */
    public static ServletOutputStream getResponseStream(String fileName) throws IOException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletResponse response = requestAttributes.getResponse();
        // 定义输出格式
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader("content-disposition", "attachment;filename="
                + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + ".xls");
        return response.getOutputStream();
    }
    /**
     * 处理异常信息
     *
     * @param error      错误信息
     * @param lineNumber 错误行数
     * @param errorMsg   错误信息
     */
    public static void dealErrorMessage(ErrorMsg error, Integer lineNumber, String errorMsg) {
        ErrorMsg.MsgInfo msgInfo = new ErrorMsg.MsgInfo();
        error.setErrorNumber(error.getErrorNumber() + 1);
        msgInfo.setLineNumber(lineNumber);
        msgInfo.setErrorMsg(errorMsg);
        error.getMsgInfo().add(msgInfo);
    }

    /**
     * 检查文件格式
     *
     * @param file 文件
     */
    public static void checkFileFormat(MultipartFile file) {
        if (file.isEmpty()) {
            throw new GlobalRuntimeException(ResponseErrorCode.PARAM_NULL_ERROR.getCode(), "未能正常接收文件或未传文件");
        }
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (!EXCEL_XLS.equals(suffix) && !EXCEL_XLSX.equals(suffix)) {
            throw new GlobalRuntimeException(ResponseErrorCode.CHECK_ERROR.getCode(), "文件格式错误，请检测文件格式");
        }
    }
}
