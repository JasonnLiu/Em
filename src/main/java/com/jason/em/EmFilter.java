package com.jason.em;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jason.em.route.Route;
import com.jason.em.route.RouteMatcher;
import com.jason.em.servlet.wrapper.Request;
import com.jason.em.servlet.wrapper.Response;
import com.jason.em.util.PathUtil;
import com.jason.em.util.ReflectUtil;

public class EmFilter implements Filter {

	private RouteMatcher rm = new RouteMatcher();

	private ServletContext servletContext;

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws  ServletException, IOException {

		Request request = new Request((HttpServletRequest) servletRequest);
		Response response = new Response((HttpServletResponse) servletResponse);

		String path = PathUtil.getRelativePath((HttpServletRequest) request);
		Route route = rm.findRoute(path);
		String viewName = null;

		if (route != null) {
			// 实际执行方法
			EmHandler eh = new EmHandler();
			viewName = eh.handle(request, response, route);
		}

		if (null != viewName) {
			response.render(viewName);
		} else {
			filterChain.doFilter((HttpServletRequest) request,
					(HttpServletResponse) response);
		}

	}

	public void init(FilterConfig filterConfig) throws ServletException {
		Em em = Em.Me();
		if (!em.isInit()) {
			String bootstrap = filterConfig.getInitParameter("bootstrap");
			Bootstrap b = getBootstrap(bootstrap);
			b.init(em);
			rm.setRoutes(em.getRouters().getList());
			this.servletContext = filterConfig.getServletContext();
			// 初始化IOC容器
			em.getIoc().init();
			em.setInit(true);
		}

	}

	private Bootstrap getBootstrap(String bootstrap) {
		if (null != bootstrap) {
			Class<?> clazz;
			Bootstrap b = null;
			try {
				clazz = Class.forName(bootstrap);
				b = (Bootstrap) clazz.newInstance();
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

			return b;
		}
		throw new RuntimeException("init bootstrap class error!");
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

}
