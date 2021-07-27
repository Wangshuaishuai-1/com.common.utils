package com.common.test;

import com.common.utils.exception.GlobalRuntimeException;
import com.common.utils.map.MapUtils;
import org.springframework.cglib.beans.BeanGenerator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarException;

public class Test {

    public static void main(String[] args) {
        HashMap<String,String> map =new HashMap<>();
        map.put("a","b");
        HashMap<String,String> map1=new HashMap<>();
        map1.put("c","d");
        HashMap<String ,String > map3=new HashMap<>();
        map1.put("a","d");
        List<Map<String,String>> list = Arrays.asList(map1,map);
        List<Object> objects = Arrays.asList(new Object(), new JarException());
        HashMap map2 = (HashMap) MapUtils.confluenceMap(list);
        System.out.println(map2.toString());

    }
}
