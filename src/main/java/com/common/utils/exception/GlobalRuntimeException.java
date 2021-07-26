package com.common.utils.exception;

/**
 * 全剧异常处理
 *
 * @author nanyanqing
 */
public class GlobalRuntimeException extends RuntimeException {

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GlobalRuntimeException(String code, String message) {
        super("status：" + code + ", message:" + message);
        this.code = code;
        this.message = message;
    }
}
