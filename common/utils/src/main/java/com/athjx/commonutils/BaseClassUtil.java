package com.athjx.commonutils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 获取字段和方法的工具类
 *
 * @author 刘朋
 * <br/>date 2020-03-05
 */
@Slf4j
public class BaseClassUtil {


    /**
     * 获取方法
     *
     * @param pageClass
     * @param methodName
     * @return
     * @author 刘朋
     * <br/>date 2020-05-24
     */
    public static Method getMethod(Class<?> pageClass, String methodName) {
        List<Method> methods = getMethods(pageClass);
        Method method = null;
        for (Method m : methods) {
            if (StringUtils.equals(m.getName(), methodName)) {
                method = m;
                break;
            }
        }
        return method;
    }

    /**
     * 获取对象的属性值
     *
     * @param o
     * @param fieldName
     * @return
     */
    public static Object getValue(Object o, String fieldName) {
        Class<?> aClass = o.getClass();
        Method method = getMethod(aClass, "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
        Object value = null;
        try {
            value = method.invoke(o);
        } catch (Exception e) {
            log.error("获取属性值异常", e);
        }
        return value;
    }


    /**
     * 设置对象的属性名
     *
     * @param o         对象
     * @param fieldName 字段名
     * @param param     属性名
     */
    public static void setValue(Object o, String fieldName, Object param) {
        Class<?> aClass = o.getClass();
        Method method = getMethod(aClass, "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
        try {
            method.invoke(o, param);
        } catch (Exception e) {
            log.error("设置属性值异常", e);
        }
    }


    /**
     * 获取所有方法,包括父类
     *
     * @param pageClass
     * @return
     * @author 刘朋
     * <br/>date 2020-03-05
     */
    public static List<Method> getMethods(Class<?> pageClass) {

        List<Method> fieldList = new ArrayList<>();
        Class tempClass = pageClass;
        while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(tempClass.getMethods()));
            tempClass = tempClass.getSuperclass();//得到父类,然后赋给自己
        }
        return fieldList;
    }


    /**
     * 获取所有的字段，包括父类
     *
     * @param pageClass
     * @return
     */
    public static List<Field> getFields(Class<?> pageClass) {
        List<Field> fieldList = new ArrayList<>();
        Class tempClass = pageClass;
        while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass();//得到父类,然后赋给自己
        }
        return fieldList;
    }

    /**
     * 获取所有的字段，包括父类， 并且字段上有annotation注解的字段。
     *
     * @param aClass
     * @param annotation
     * @return
     */
    public static List<Field> getFields(Class<?> aClass, Class<? extends Annotation> annotation) {
        List<Field> fields = getFields(aClass);
        List<Field> resultList = new ArrayList<>();
        for (Field field : fields) {
            Annotation ann = field.getAnnotation(annotation);
            Annotation[] annotationsByType = field.getAnnotationsByType(annotation);
            if (Objects.nonNull(ann) || CollectionUtils.isNotEmpty(annotationsByType)) {
                resultList.add(field);
            }
        }
        return resultList;
    }
}
