package org.liujiaxin.jaweb.mvc.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExecutionChain {

    private Handler handler;

    private List<Interceptor> interceptors;

    int index = 0;

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }

    public ExecutionChain(Handler handler, List<Interceptor> interceptors) {
        super();
        this.handler = handler;
        this.interceptors = interceptors;
    }

    public Object exeInterceptor() throws Exception {
        boolean flag = true;
        for (int i = 0; i < interceptors.size(); i++) {
            index = i;
            if (!interceptors.get(i).doInterceptor()) {
                flag = false;
                break;
            }

        }
        if (flag) {
            Object result = handler.execute();
            return result;
        }
        return null;

    }

    public void exeAfterInterceptor() {
        if (interceptors.size() != 0) {
            for (int i = index; i >= 0; i--) {
                interceptors.get(i).afterInterceptor();
            }
        }
    }

    public boolean applyPreHandle(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        return true;
    }

    public void applyPostHandle(HttpServletRequest request, HttpServletResponse response, ResponseInfo ri) {
        // TODO Auto-generated method stub

    }

}
