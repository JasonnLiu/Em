package org.liujiaxin.jaweb.mvc.servlet.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class DefaultRequest extends HttpServletRequestWrapper{

    public DefaultRequest(HttpServletRequest request) {
        super(request);

    }
}
