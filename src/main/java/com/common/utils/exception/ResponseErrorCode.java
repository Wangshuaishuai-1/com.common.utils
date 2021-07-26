package com.common.utils.exception;

/**
 * 全局异常码
 *
 * @author nanyanqing
 */

public enum ResponseErrorCode {

    /*----------------------------------------   客户端异常   ----------------------------------------------*/


    /*----------------------------------------   服务端异常   ----------------------------------------------*/
    /**
     * 通用服务异常 ：500**
     */
    RUNTIME_ERROR("50001", "运行时异常"),

    /**
     * 501**
     */
    JSON_ERROR("50110","json序列化失败")

    /**
     * 502**
     */


    /**
     * 503**
     */

    /**
     * 504**
     */


    /**
     * 505**
     */


    /**
     * 506**
     */

    ;

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
