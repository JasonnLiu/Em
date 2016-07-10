package org.liujiaxin.jaweb.mvc.handler;

public interface Interceptor {
    void destory();

    void init();

    boolean doInterceptor();

    void afterInterceptor();

}
