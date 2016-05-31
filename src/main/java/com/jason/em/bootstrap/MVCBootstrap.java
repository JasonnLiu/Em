package com.jason.em.bootstrap;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jason.em.Em;
import com.jason.em.annotation.Controller;
import com.jason.em.annotation.RequestMapping;

public class MVCBootstrap implements Bootstrap {

	private ApplicationContext applicationContext;

	ServletContext context;

	private List<Object> controllerBeans = new ArrayList<Object>();

	public void setContext(ServletContext context) {
		this.context = context;
	}

	public void init(Em em) {

		applicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);
		initBeans();
		createRoute(em);
		// em.addRoute(path, methodName, controller);

	}

	private void createRoute(Em em) {
		for (Object o : controllerBeans) {
			addUrlMather(o, em);
		}
	}

	private void addUrlMather(Object obj, Em em) {
		Class clazz = obj.getClass();
		Method[] method = clazz.getMethods();

		for (int i = 0; i < method.length; i++) {
			if (isLegalMethod(method[i])) {

				String annotation = method[i].getAnnotation(
						RequestMapping.class).value();
				em.getRouters().addRoute(annotation, method[i], obj);

			}

		}

	}

	private void initBeans() {
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		for (String beanName : beanNames) {

			if (applicationContext.getBean(beanName) instanceof Controller
					|| applicationContext.getType(beanName)
							.isAnnotationPresent(Controller.class) == true) {
				controllerBeans.add(applicationContext.getBean(beanName));
			}

		}

	}

	private boolean isLegalMethod(Method method) {
		RequestMapping requestURI = method.getAnnotation(RequestMapping.class);

		if (requestURI == null || requestURI.value().length() == 0) {// 没有该注解默认不是
			return false;
		}

		if (Modifier.isStatic(method.getModifiers())) {// 不能使静态方法

			return false;
		}

		// 返回值是否是这3种
		Class<?> retType = method.getReturnType();
		if (retType.equals(void.class) || retType.equals(String.class)

		) {

			return true;
		}

		return false;
	}

}
