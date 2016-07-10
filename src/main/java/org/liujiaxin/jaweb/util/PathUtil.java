package org.liujiaxin.jaweb.util;

import javax.servlet.http.HttpServletRequest;

public class PathUtil {

	public static String getRelativePath(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String absolutePath = request.getRequestURI();// URI not URL
		String path = absolutePath.substring(contextPath.length());
		return path;
	}

}
