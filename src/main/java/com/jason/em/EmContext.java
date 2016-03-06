package com.jason.em;

import javax.servlet.ServletContext;

import com.jason.em.servlet.wrapper.Request;
import com.jason.em.servlet.wrapper.Response;

public class EmContext {
	
	private static final  ThreadLocal<EmContext> CONTEXT = new ThreadLocal<EmContext>();
	
	private ServletContext sc;
	private Request req;
	private Response resp;
	
	public static EmContext Me(){
		return CONTEXT.get();
	}

	public ServletContext getSc() {
		return sc;
	}

	public void setSc(ServletContext sc) {
		this.sc = sc;
	}

	public Request getReq() {
		return req;
	}

	public void setReq(Request req) {
		this.req = req;
	}

	public Response getResp() {
		return resp;
	}

	public void setResp(Response resp) {
		this.resp = resp;
	}

	public static ThreadLocal<EmContext> getContext() {
		return CONTEXT;
	}

	public static void init(Request req,Response resp,ServletContext sc) {
		EmContext ec = new EmContext();
		ec.setReq(req);
		ec.setResp(resp);
		ec.setSc(sc);
		EmContext.CONTEXT.set(ec);
		
	}
	
	
	
	

}
