package com.common.utils.bean;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BulkBean;

import java.util.Map;

/**
 * 对象的工具类
 *
 * @author nanyanqing
 */
public class BeanUtils {

    /**
     * 复制对象
     * @param source
     * @param reject
     * @param yes
     * @return
     */
    public static Object copy(Object source,Object reject,Boolean yes){
        BeanCopier beanCopier = BeanCopier.create(source.getClass(), reject.getClass(), yes);
        beanCopier.copy(source,reject.getClass(),null);
        return reject;
    }

    /**
     * 动态创建对象
     * @param map
     * @return
     */
    public static Object creatObject(Map map){
        // 设置属性
        BeanGenerator beanGenerator=new BeanGenerator();
        // 设置get，set方法
        BulkBean bulkBean = BulkBean.create();
    }
}
