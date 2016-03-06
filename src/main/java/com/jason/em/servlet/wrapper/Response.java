package com.jason.em.servlet.wrapper;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import com.jason.em.Em;
import com.jason.em.render.Render;

public class Response {

	private HttpServletResponse rawResponse;
	
	private Render render;
	

	public Response(HttpServletResponse httpServletResponse) {
		this.rawResponse = httpServletResponse;
		this.render = Em.Me().getRender();
		this.getRawResponse().setHeader("Framework", "Em");
	}
	
	public void render(String view){
		render.render(view, null);
		
	}

	public HttpServletResponse getRawResponse() {
		return rawResponse;
	}

	public void setRawResponse(HttpServletResponse rawResponse) {
		this.rawResponse = rawResponse;
	}
	
	

}
