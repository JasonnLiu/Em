package com.jason.em.servlet.wrapper;

import javax.servlet.http.HttpServletRequest;

public class Request {
	
	private HttpServletRequest rawRequest;

	public Request(HttpServletRequest httpServletRequest) {
		this.rawRequest = httpServletRequest;
	}

	public HttpServletRequest getRawRequest() {
		return rawRequest;
	}

	public void setRawRequest(HttpServletRequest rawRequest) {
		this.rawRequest = rawRequest;
	}
	

}
