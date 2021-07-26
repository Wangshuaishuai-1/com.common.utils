package com.common.test;

import com.common.utils.exception.GlobalRuntimeException;
import com.common.utils.map.MapUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        HashMap<Object,Object> map =new HashMap<>();
        map.put("a","b");
        HashMap<Object,Object> map1=new HashMap<>();
        map1.put("c","d");
        HashMap<Object,Object> map3=new HashMap<>();
        map1.put("a","d");
        List<HashMap<Object,Object>> list = Arrays.asList(map1,map);
        HashMap map2 = MapUtils.confluenceMap(list);
        System.out.println(map2.toString());
        throw new GlobalRuntimeException("100","错误的操作");

    }
}
