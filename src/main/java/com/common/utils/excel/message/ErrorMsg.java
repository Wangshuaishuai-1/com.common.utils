package com.common.utils.excel.message;

import java.util.List;

/**
 * 错误信息类
 *
 * @author nanyanqing
 */
public class ErrorMsg {

    private Integer errorNumber;

    private List<MsgInfo> msgInfo;

    public ErrorMsg() {
    }

    public ErrorMsg(Integer errorNumber, List<MsgInfo> msgInfo) {
        this.errorNumber = errorNumber;
        this.msgInfo = msgInfo;
    }

    public Integer getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(Integer errorNumber) {
        this.errorNumber = errorNumber;
    }

    public List<MsgInfo> getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(List<MsgInfo> msgInfo) {
        this.msgInfo = msgInfo;
    }

    public static class MsgInfo {
        private Integer lineNumber;
        private String errorMsg;

        public Integer getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(Integer lineNumber) {
            this.lineNumber = lineNumber;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }
    }

}
