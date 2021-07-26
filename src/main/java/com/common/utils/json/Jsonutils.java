package com.common.utils.json;

import com.common.utils.exception.GlobalRuntimeException;
import com.common.utils.exception.ResponseErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * @author nanyanqing
 */
public class Jsonutils {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectReader OBJECT_READER = MAPPER.reader();
    private static final ObjectWriter OBJECT_WRITER =MAPPER.writer();

    {
        MAPPER.getSerializationConfig().with(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
    }

    /**
     * json：对象转化为字符串
     * @param object
     * @return
     */
    public static String toString(Object object){
        try {
            return OBJECT_WRITER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new GlobalRuntimeException(ResponseErrorCode.JSON_ERROR.getCode(),ResponseErrorCode.JSON_ERROR.getMessage());
        }
    }

    /**
     * json：字符串转化为对象
     * @param content
     * @param valueType
     * @param <T>
     * @return
     */
    public static <T> T parseToObject(String content, Class<T> valueType){
        try {
            return OBJECT_READER.readValue(content, valueType);
        } catch (IOException e) {
            throw new GlobalRuntimeException(ResponseErrorCode.JSON_ERROR.getCode(),ResponseErrorCode.JSON_ERROR.getMessage());
        }
    }

    /**
     * json：对象转化为map
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static HashMap parseToMap(Object object){
        try {
            String content = OBJECT_WRITER.writeValueAsString(object);
            return OBJECT_READER.readValue(content, HashMap.class);
        } catch (IOException e) {
           throw new GlobalRuntimeException(ResponseErrorCode.JSON_ERROR.getCode(),ResponseErrorCode.JSON_ERROR.getMessage());
        }
    }

    /**
     * json：map转化为对象
     * @param content
     * @param toValueType
     * @param <T>
     * @return
     */
    public static <T> T parseMapToObject(HashMap content, Class<T> toValueType) {
        return MAPPER.convertValue(content, toValueType);
    }

    /**
     * json：将字符串转化为JsonNode
     * @param content
     * @return
     * @throws JsonProcessingException
     */
    public static JsonNode parseToJsonNode(String content){
        try {
            return OBJECT_READER.readTree(content);
        } catch (JsonProcessingException e) {
            throw new GlobalRuntimeException(ResponseErrorCode.JSON_ERROR.getCode(),ResponseErrorCode.JSON_ERROR.getMessage());
        }
    }

    /**
     * json：将对象转化为JsonNode
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static JsonNode parseToJsonNode(Object object) {
        return MAPPER.convertValue(object,JsonNode.class);
    }

}
