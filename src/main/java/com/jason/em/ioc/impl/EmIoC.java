package com.jason.em.ioc.impl;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jason.em.ioc.Container;
import com.jason.em.util.ReflectUtil;

public class EmIoC implements Container {

	/*
	 * com.xx.xx -- Object
	 */
	private Map<String, Object> beans;

	/*
	 * name -- com.xx.xx
	 */
	private Map<String, String> beanKeys;

	public Map<String, Object> getBeans() {
		return beans;
	}

	public void setBeans(Map<String, Object> beans) {
		this.beans = beans;
	}

	public Map<String, String> getBeanKeys() {
		return beanKeys;
	}

	public void setBeanKeys(Map<String, String> beanKeys) {
		this.beanKeys = beanKeys;
	}

	public EmIoC() {
		this.beans = new ConcurrentHashMap<String, Object>();
		this.beanKeys = new ConcurrentHashMap<String, String>();
	}

	public <T> T getBean(Class<T> clazz) {
		String name = clazz.getName();
		Object o = beans.get(name);
		if (null != o) {
			return (T) o;
		}
		return null;
	}

	public <T> T getBeanByName(String name) {
		String className = beanKeys.get(name);
		Object obj = beans.get(className);
		if (null != obj) {
			return (T) obj;
		}
		return null;
	}

	public <T> T getBeanByClassName(String className) {
		Object obj = beans.get(className);
		if (null != obj) {
			return (T) obj;
		}
		return null;
	}

	public Object registerBean(Object bean) {
		String className = bean.getClass().getName();
		beans.put(className, bean);
		beanKeys.put(className, className);
		return bean;
	}

	public Object registerBean(Class<?> clazz) {
		String className = clazz.getName();
		Object obj = null;
		try {
			obj = clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		beans.put(className, obj);
		beanKeys.put(className, className);
		return obj;
	}

	public Object registerBean(String name, Object bean) {
		String className = bean.getClass().getName();
		beans.put(className, bean);
		beanKeys.put(name, className);
		return bean;
	}

	public Object registerBean(String name, String className, Object bean) {
		beans.put(className, bean);
		beanKeys.put(name, className);
		return bean;
	}

	public void remove(Class<?> clazz) {
		String className = clazz.getName();
		if (null != className && !className.equals("")) {
			beanKeys.remove(className);
			beans.remove(className);
		}

	}

	public void removeByName(String name) {
		String className = beanKeys.get(name);
		if (null != className && !className.equals("")) {
			beanKeys.remove(className);
			beans.remove(className);
		}
	}

	public Set<String> getBeanNames() {
		return beanKeys.keySet();
	}

	public void initWired() {
		Iterator<Entry<String, Object>> it = beans.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it
					.next();
			Object object = entry.getValue();
			wired(object);
		}

	}

	private void wired(Object obj) {

	}

	public void initWired(Object object, Map<String, Object> prop) {
		Class<?> clazz = object.getClass();
		for (Map.Entry<String, Object> e : prop.entrySet()) {
			try {
				Field f = clazz.getDeclaredField(e.getKey());
				f.setAccessible(true);
				f.set(object, e.getValue());
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void init() {
		File f = new File("src/main/java/config.xml");
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(f);
			Element root = doc.getRootElement();
			List<Element> elements = root.elements();
			for (Element e : elements) {
				if (e.getName().equals("bean")) {
					// System.out.print(e.getName());
					// System.out.print(":");

					/*
					 * 默认每个bean就只有两个Attribute，id和class 注册bean
					 */
					String id = e.attributeValue("id");
					String className = e.attributeValue("class");
					Object obj = null;
					if (null != id && !id.equals("") && null != className
							&& !className.equals("")) {
						obj = ReflectUtil.newInstance(className);
						registerBean(id, className, obj);

					}

					/*
					 * 默认bean元素的子元素都是property <property name=""
					 * value=""></property> 或者<property name=""
					 * ref=""></property>
					 */
					// 
					Map<String, Object> propMap = new HashMap<String, Object>();
					List<Element> propEle = e.elements();
					for (Element prop : propEle) {
						String propName = prop.attributeValue("name");
						Object propVal;
						if (null != prop.attributeValue("value")) {
							propVal = prop.attributeValue("value");
						} else {
							String ref = prop.attributeValue("ref");
							propVal = getBeanByName(ref);
						}
						propMap.put(propName, propVal);
					}
					initWired(obj, propMap);
				}
			}

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object replaceBean(String classname,Object nobj) {
		beans.put(classname, nobj);
		return nobj;
	}

}
