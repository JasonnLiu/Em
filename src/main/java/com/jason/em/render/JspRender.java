package com.jason.em.render;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jason.em.EmContext;

public class JspRender implements Render {

	public void render(String view, Writer writer) {
		String viewPath = getViewPath(view);
		
		HttpServletRequest servletRequest = EmContext.Me().getReq().getRawRequest();
		HttpServletResponse servletResponse = EmContext.Me().getResp().getRawResponse();
		
		try {
			servletRequest.getRequestDispatcher(viewPath).forward(servletRequest, servletResponse);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String getViewPath(String view) {
		String viewPrfix = "/";
		String viewSuffix = ".jsp";
		StringBuilder sb = new StringBuilder(viewPrfix);
		sb.append(view).append(viewSuffix);
		return sb.toString();
	}

}
