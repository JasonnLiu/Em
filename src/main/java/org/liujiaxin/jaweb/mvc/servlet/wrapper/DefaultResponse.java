package org.liujiaxin.jaweb.mvc.servlet.wrapper;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


public class DefaultResponse extends HttpServletResponseWrapper {

    public DefaultResponse(HttpServletResponse response) {
        super(response);
    }


}
