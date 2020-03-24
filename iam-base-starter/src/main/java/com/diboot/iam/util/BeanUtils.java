package com.diboot.iam.util;

import com.diboot.core.util.IGetter;
import com.diboot.core.util.V;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BeanUtils extends com.diboot.core.util.BeanUtils {

    private static final Logger log = LoggerFactory.getLogger(BeanUtils.class);

    /**
     * 从list对象列表中提取指定属性值到新的List
     * @param objectList 对象list
     * @param getterFn get方法
     * @param <T>
     * @return
     */
    public static <E,T> List collectToList(List<E> objectList, IGetter<T> getterFn){
        if(V.isEmpty(objectList)){
            return Collections.emptyList();
        }
        String getterPropName = convertToFieldName(getterFn);
        return collectToList(objectList, getterPropName);
    }

    /***
     * 从list对象列表中提取指定属性值到新的List
     * @param objectList
     * @param getterPropName
     * @param <E>
     * @return
     */
    public static <E> List collectToList(List<E> objectList, String getterPropName){
        List fieldValueList = new ArrayList();
        try{
            for(E object : objectList){
                Object fieldValue = getProperty(object, getterPropName);
                if(fieldValue != null && !fieldValueList.contains(fieldValue)){
                    fieldValueList.add(fieldValue);
                }
            }
        }
        catch (Exception e){
            log.warn("提取属性值异常, getterPropName="+getterPropName, e);
        }
        return fieldValueList;
    }

    /***
     * 获取对象的属性值
     * @param obj
     * @param field
     * @return
     */
    public static Object getProperty(Object obj, String field){
        try {
            BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
            return wrapper.getPropertyValue(field);
        } catch (Exception e) {
            log.error("获取对象属性值出错，返回null", e);
        }
        return null;
    }
}
