package com.jason.em.aop;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jason.em.ioc.Container;
import com.jason.em.util.ReflectUtil;

public class AopService {

	private Container ioc;

	public Container getIoc() {
		return ioc;
	}

	public void setIoc(Container ioc) {
		this.ioc = ioc;
	}

	

	public void init() {
		File f = new File("src/main/java/config.xml");
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(f);
			Element root = doc.getRootElement();
			List<Element> elements = root.elements();
			for (Element e : elements) {
				if (e.getName().equals("aop")) {
					List<Element> aspects = e.elements();
					for (Element aspect : aspects) {
						handle(aspect);
					}

				}
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void handle(Element a) {
		String beanName = a.attributeValue("class");
		Object aspectbean = ioc.getBeanByClassName(beanName);
		// a_attr是aspect的子元素集合
		List<Element> a_attr = a.elements();
		String pointcut_class = null;
		String pointcut_method = null;
		String before = null;
		String after = null;
		for (Element e : a_attr) {
			if (e.getName().equals("pointcut")) {
				pointcut_class = e.attributeValue("class");
				pointcut_method = e.attributeValue("method");
			}
			if (e.getName().equals("before")) {
				before = e.attributeValue("method");
			}
			if (e.getName().equals("after")) {
				after = e.attributeValue("method");
			}
		}
		Class<?> aspectClazz = aspectbean.getClass();
		Object point = ioc.getBeanByClassName(pointcut_class);
		Class<?> clazz = point.getClass();
		try {
			Method pointM = clazz.getMethod(pointcut_method);
			Method beforeM = aspectClazz.getMethod(before);
			Method afterM = aspectClazz.getMethod(after);

			// beforeM.invoke(abean);
			// pointM.invoke(point);
			// afterM.invoke(abean);

			// pointM也要传进去
			Object proxy = ReflectUtil.newProxy(point, pointM, beforeM, afterM,
					aspectbean);
			ioc.replaceBean(pointcut_class, proxy);

		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
