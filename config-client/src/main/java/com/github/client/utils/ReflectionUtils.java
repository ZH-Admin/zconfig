package com.github.client.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hangs.zhang
 * @date 2018/8/14
 * *********************
 * function: 反射工具类
 */
public class ReflectionUtils {

    private ReflectionUtils() {
    }

    public static Field[] getFields(Class<?> clazz) {
        Preconditions.checkArgument(clazz != null, "clazz is null");

        return clazz.getDeclaredFields();
    }

    public static Field[] getFields(Object obj) {
        Preconditions.checkArgument(obj != null, "obj is null");

        Class<?> clazz = obj.getClass();
        return getFields(clazz);
    }

    public static List<Field> getFieldsWithAnnotation(Class<?> clazz, Class annotation) {
        Preconditions.checkArgument(ObjectUtils.allNotNull(clazz, annotation), "clazz or annotation is null");

        Field[] fields = getFields(clazz);
        List<Field> result = Lists.newArrayList();
        if (ArrayUtils.isEmpty(fields)) {
            return result;
        }

        for (Field field : fields) {
            Annotation anno = field.getAnnotation(annotation);
            if (anno != null) {
                result.add(field);
            }
        }
        return result;
    }

    public static List<Field> getFieldsWithAnnotation(Object obj, Class annotation) {
        Preconditions.checkArgument(ObjectUtils.allNotNull(obj, annotation), "obj or annotation is null");

        Class<?> clazz = obj.getClass();
        return getFieldsWithAnnotation(clazz, annotation);
    }

    public static Object getFieldContent(Object obj, Field field) {
        Preconditions.checkArgument(ObjectUtils.allNotNull(obj, field), "obj or field is null");

        Object result = null;
        try {
            field.setAccessible(true);
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    public static void setFieldContent(Object obj, Field field, Object value) {
        Preconditions.checkArgument(ObjectUtils.allNotNull(obj, field, value), "obj or field or value is null");

        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Method[] getMethods(Class clazz) {
        Preconditions.checkArgument(clazz != null, "clazz is null");

        return clazz.getDeclaredMethods();
    }

    public static Method[] getMethods(Object obj) {
        return getMethods(obj.getClass());
    }

    public static List<Method> getMethodContainAnnotation(Object obj, Class annotation) {
        Preconditions.checkArgument(ObjectUtils.allNotNull(obj, annotation), "obj or annotation is null");

        return getMethodContainAnnotation(obj.getClass(), annotation);
    }

    public static List<Method> getMethodContainAnnotation(Class clazz, Class annotation) {
        Preconditions.checkArgument(ObjectUtils.allNotNull(clazz, annotation), "clazz or annotation is null");

        List<Method> result = new ArrayList<>();
        Method[] methods = getMethods(clazz);
        for (Method method : methods) {
            Annotation anno = method.getAnnotation(annotation);
            if (anno != null) {
                result.add(method);
            }
        }
        return result;
    }

    public static Object invokeMethod(Method method, Object obj) {
        Preconditions.checkArgument(ObjectUtils.allNotNull(method, obj), "method or obj is null");

        Object result = null;
        try {
            method.setAccessible(true);
            result = method.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException("方法执行异常, message:" + e.getMessage());
        }
        return result;
    }

}
