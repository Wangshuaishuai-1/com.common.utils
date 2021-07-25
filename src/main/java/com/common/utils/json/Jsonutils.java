package com.common.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

/**
 * @author nanyanqing
 */
public class Jsonutils {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * json：对象转化为字符串
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static String toString(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    /**
     * json：字符串转化为对象
     * @param content
     * @param valueType
     * @param <T>
     * @return
     * @throws JsonProcessingException
     */
    public static <T> T parseToObject(String content, Class<T> valueType) throws JsonProcessingException {
        return mapper.readValue(content, valueType);
    }

    /**
     * json：对象转化为map
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static HashMap<String, String> parseToMap(Object object) throws JsonProcessingException {
        String content = mapper.writeValueAsString(object);
        HashMap result = mapper.readValue(content, HashMap.class);
        return result;
    }

    /**
     * json：map转化为对象
     * @param content
     * @param toValueType
     * @param <T>
     * @return
     */
    public static <T> T parseMapToObject(HashMap content, Class<T> toValueType) {
        return mapper.convertValue(content, toValueType);
    }

}
