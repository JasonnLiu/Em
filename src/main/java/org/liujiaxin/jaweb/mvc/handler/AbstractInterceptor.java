package org.liujiaxin.jaweb.mvc.handler;

public abstract class AbstractInterceptor implements Interceptor {

	public void destory() {

	}

	public void init() {
		
	}

	public abstract boolean  doInterceptor();
	
	public void afterInterceptor() {
		
	}

}
