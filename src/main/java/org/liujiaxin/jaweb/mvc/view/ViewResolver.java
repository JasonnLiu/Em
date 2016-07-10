package org.liujiaxin.jaweb.mvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.liujiaxin.jaweb.mvc.enumeration.ResponseType;
import org.liujiaxin.jaweb.mvc.handler.ResponseInfo;

public interface ViewResolver {

    void resolveView(HttpServletRequest request, HttpServletResponse response, ResponseInfo ri);

    boolean support(ResponseType type);
}
