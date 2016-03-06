package com.jason.em;

import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jason.em.route.Route;
import com.jason.em.servlet.wrapper.Request;
import com.jason.em.servlet.wrapper.Response;
import com.jason.em.util.ReflectUtil;

public class EmHandler {

	private ServletContext servletContext;

	public String handle(Request request, Response response, Route route) {

		Object controller = route.getController();
		Method actionMethod = route.getAction();
		EmContext.init(request, response, servletContext);
		String viewName = (String) executeMethod(controller, actionMethod,
				request, response);
		return viewName;

	}

	private Object executeMethod(Object controller, Method actionMethod,
			Request request, Response response) {
		int len = actionMethod.getParameterTypes().length;
		actionMethod.setAccessible(true);
		if (len > 0) {
			Object[] args = getArgs(request, response,
					actionMethod.getParameterTypes());
			return ReflectUtil.invokeMethod(controller, actionMethod, args);
		} else {
			return ReflectUtil.invokeMethod(controller, actionMethod);
		}

	}

	private Object[] getArgs(Request request, Response response,
			Class<?>[] parameterTypes) {
		int len = parameterTypes.length;
		Object[] args = new Object[len];
		for (int i = 0; i < len; i++) {
			Class<?> clazz = parameterTypes[i];
			if (clazz.getName().equals(request.getClass().getName())) {
				args[i] = request;
			}
			if (clazz.getName().equals(response.getClass().getName())) {
				args[i] = response;
			}
		}
		return args;
	}

}
