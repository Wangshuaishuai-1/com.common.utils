package com.common.utils.map;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author nanyanqing
 */
public class MapUtils {

    /**
     * 合并多个map，重复key取最新的value
     *
     * @param mapList
     */
    public static HashMap confluenceMap(List<HashMap<Object, Object>> mapList) {
        AtomicInteger size = new AtomicInteger(1);
        while (size.get() < mapList.size()) {
            HashMap<Object, Object> mapOne = mapList.get(size.get() - 1);
            HashMap<Object, Object> mapTwo = mapList.get(size.get());
            mapOne.keySet().forEach(map -> mapTwo.merge(map, mapOne.get(map), (x1, x2) -> x2));
            mapList.set(size.get(), mapTwo);
            size.addAndGet(1);
        }
        return mapList.get(mapList.size()-1);
    }
}
