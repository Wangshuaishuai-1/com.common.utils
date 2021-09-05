package com.common.utils.excel.message;

import java.util.List;
import java.util.Map;

/**
 * 导入结果集
 *
 * @author nanyanqing
 */
public class ExcelResult {

    List<Object> result;

    ErrorMsg errorMsg;


    public ExcelResult() {
    }

    public ExcelResult(List<Object> result, ErrorMsg errorMsg) {
        this.result = result;
        this.errorMsg = errorMsg;
    }

    public List<Object> getResult() {
        return result;
    }

    public void setResult(List<Object> result) {
        this.result = result;
    }

    public ErrorMsg getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(ErrorMsg errorMsg) {
        this.errorMsg = errorMsg;
    }
}
