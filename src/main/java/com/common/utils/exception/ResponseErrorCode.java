package com.common.utils.exception;

/**
 * 全局异常码
 *
 * @author nanyanqing
 */

public enum ResponseErrorCode {
    /**
     * 通用异常
     */
    RUNTIME_ERROR("500", "运行时异常"),
    JSON_ERROR("50101","json序列化失败");

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResponseErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
