package org.liujiaxin.jaweb.mvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.liujiaxin.jaweb.mvc.enumeration.ResponseType;
import org.liujiaxin.jaweb.mvc.handler.ResponseInfo;
import org.liujiaxin.jaweb.util.ResponseUtil;

public class JsonViewResolver implements ViewResolver {

    @Override
    public void resolveView(HttpServletRequest request, HttpServletResponse response, ResponseInfo ri) {
        Object result = ri.getResult();
        ResponseUtil.writeJSON(response, result);
    }

    @Override
    public boolean support(ResponseType type) {
        return type == ResponseType.JSON;
    }

}
