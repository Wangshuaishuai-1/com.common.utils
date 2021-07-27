package com.common.utils.map;

import org.springframework.cglib.beans.BeanMap;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author nanyanqing
 */
public class MapUtils {

    /**
     * 合并多个map，重复key取最新的value
     *
     * @param mapList
     * @return
     */
    public static <T> Map<T,T> confluenceMap(List<Map<T,T>> mapList) {
        AtomicInteger size = new AtomicInteger(1);
        while (size.get() < mapList.size()) {
            Map<T,T> mapOne = mapList.get(size.get() - 1);
            Map<T,T> mapTwo = mapList.get(size.get());
            mapOne.keySet().forEach(map -> mapTwo.merge(map, mapOne.get(map), (x1, x2) -> x2));
            mapList.set(size.get(), mapTwo);
            size.addAndGet(1);
        }
        return mapList.get(mapList.size()-1);
    }

    /**
     * 将对象转化为map
     * @param objects
     * @return
     */
    public static Map<Object, Object> parseObjectToMap(List<Object> objects){
        List<Map<Object,Object>> beanMaps = objects.stream().map(BeanMap::create).collect(Collectors.toList());
        return confluenceMap(beanMaps);
    }

}
