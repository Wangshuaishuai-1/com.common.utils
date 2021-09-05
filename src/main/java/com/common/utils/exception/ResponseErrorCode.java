package com.common.utils.exception;

/**
 * 全局异常码
 *
 * @author nanyanqing
 */

public enum ResponseErrorCode {

    /*----------------------------------------   客户端异常   ----------------------------------------------*/
    /**
     * 通用检测：400**
     */
    CHECK_ERROR("40001", "检测异常"),

    /**
     * 参数检测：401**
     */
    PARAM_NULL_ERROR("40101","参数传输错误"),
    /*----------------------------------------   服务端异常   ----------------------------------------------*/

    /**
     * 通用服务异常 ：500**
     */
    RUNTIME_ERROR("50001", "运行时异常"),

    /**
     * 501**
     */
    JSON_ERROR("50110", "json序列化失败"),
    ANALYSIS_ERROR("50102","解析时出现异常！"),

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
