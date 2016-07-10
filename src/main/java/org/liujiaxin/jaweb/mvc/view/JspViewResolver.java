package org.liujiaxin.jaweb.mvc.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.liujiaxin.jaweb.mvc.enumeration.ResponseType;
import org.liujiaxin.jaweb.mvc.handler.ResponseInfo;

public class JspViewResolver implements ViewResolver {

    private final String viewPrfix = "/";

    private final String viewSuffix = ".jsp";

    @Override
    public void resolveView(HttpServletRequest request, HttpServletResponse response, ResponseInfo ri) {

        String viewName = (String) ri.getResult();
        String viewPath = getViewPath(viewName);
        try {
            request.getRequestDispatcher(viewPath).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean support(ResponseType type) {
        return type == ResponseType.JSP;
    }

    private String getViewPath(String view) {
        StringBuilder sb = new StringBuilder(viewPrfix);
        sb.append(view).append(viewSuffix);
        return sb.toString();
    }

}
