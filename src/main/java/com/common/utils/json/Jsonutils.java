package com.common.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author nanyanqing
 */
public class Jsonutils {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectReader objectReader = mapper.reader();
    private static final ObjectWriter objectWriter=mapper.writer();

    /**
     * json：对象转化为字符串
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static String toString(Object object) throws JsonProcessingException {
        return objectWriter.writeValueAsString(object);
    }

    /**
     * json：字符串转化为对象
     * @param content
     * @param valueType
     * @param <T>
     * @return
     * @throws JsonProcessingException
     */
    public static <T> T parseToObject(String content, Class<T> valueType) throws IOException {
        return objectReader.readValue(content, valueType);
    }

    /**
     * json：对象转化为map
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static HashMap<String, String> parseToMap(Object object) throws IOException {
        String content = objectWriter.writeValueAsString(object);
        HashMap result = objectReader.readValue(content, HashMap.class);
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

    /**
     * json：将字符串转化为JsonNode
     * @param content
     * @return
     * @throws JsonProcessingException
     */
    public static JsonNode parseToJsonNode(String content) throws JsonProcessingException {
        return objectReader.readTree(content);
    }

    /**
     * json：将对象转化为JsonNode
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static JsonNode parseToJsonNode(Object object) {
        return mapper.convertValue(object,JsonNode.class);
    }

}
