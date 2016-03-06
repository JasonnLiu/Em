package com.jason.em.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ReflectUtil {

	public static Object invokeMethod(Object controller, Method actionMethod,
			Object[] args) {
		Object value = null;
		try {
			value = actionMethod.invoke(controller, args);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public static Object invokeMethod(Object controller, Method actionMethod) {
		Object value = null;
		try {
			value = actionMethod.invoke(controller, null);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public static Object newInstance(String className) {
		ClassLoader loader = ReflectUtil.class.getClassLoader();
		Class<?> clazz;
		Object obj = null;
		try {
			clazz = loader.loadClass(className);
			obj = clazz.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj;
	}

	
	public static Object newProxy(final Object point, final Method pointM,final Method beforeM, final Method afterM, final Object abean  ){
		
		Object proxy = null;
		
		proxy = Proxy.newProxyInstance(ReflectUtil.class.getClassLoader(), point.getClass().getInterfaces(), new InvocationHandler(){

			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				if(method.getName().equals(pointM.getName())){
					beforeM.invoke(abean);
				}
				Object tmp = method.invoke(point, args);
				if(method.getName().equals(pointM.getName())){
					afterM.invoke(abean);
				}
				
				return tmp;
			}
			
		});
		
		return proxy;
	}
}
