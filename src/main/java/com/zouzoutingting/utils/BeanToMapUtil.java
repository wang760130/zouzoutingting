package com.zouzoutingting.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * JavaBean对象与Map对象互相转化
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月10日
 */
public class BeanToMapUtil {

	/**
	 * 将一个 Map 对象转化为一个 JavaBean
	 * @param type 要转化的类型 
	 * @param map 包含属性值的 map 
	 * @return 转化出来的 JavaBean 对象 
	 * @throws IntrospectionException  如果分析类属性失败 
     * @throws IllegalAccessException  如果实例化 JavaBean 失败 
     * @throws InstantiationException  如果实例化 JavaBean 失败 
     * @throws InvocationTargetException  如果调用属性的 setter 方法失败 
	 */
	public static Object convertMap(Class<?> type, Map<String, Object> map)   throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		// 获取类属性 
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		// 创建 JavaBean 对象 
		Object obj = type.newInstance(); 
		
		// 给 JavaBean 对象的属性赋值 
		PropertyDescriptor[] propertyDescriptor = beanInfo.getPropertyDescriptors();
		for(int i = 0; i < propertyDescriptor.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptor[i];
			String propertyName = descriptor.getName();
			if(map.containsKey(propertyName)) {
				try {
					Object value = map.get(propertyName);
					Object[] args = new Object[1];
					args[0] = value;
					descriptor.getWriteMethod().invoke(obj, args);
				} catch (IllegalArgumentException e) {					
				} catch (InvocationTargetException e) {
				}
			}
		}
		
		return obj;
	}
	
	/** 
     * 将一个 JavaBean 对象转化为一个  Map 
     * @param bean 要转化的JavaBean 对象 
     * @return 转化出来的  Map 对象 
     * @throws IntrospectionException 如果分析类属性失败 
     * @throws IllegalAccessException 如果实例化 JavaBean 失败 
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败 
     */ 
	public static Map<String, Object> convertBean(Object bean) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class<?> type = bean.getClass();
		Map<String, Object> map = new HashMap<String, Object>();
		
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptor = beanInfo.getPropertyDescriptors();
		for(int i = 0; i < propertyDescriptor.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptor[i];
			String propertyName = descriptor.getName();
			if(!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if(result != null) {
					map.put(propertyName, result);
				} else {
					map.put(propertyName, "");
				}
			}
		}
		
		return map;
	}
}
