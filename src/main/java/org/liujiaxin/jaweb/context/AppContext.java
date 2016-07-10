package org.liujiaxin.jaweb.context;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.liujiaxin.jaweb.aop.AopHelper;
import org.liujiaxin.jaweb.ioc.DefaultBeanFactory;
import org.liujiaxin.jaweb.ioc.SimpleBeanFactory;
import org.liujiaxin.jaweb.mvc.annotation.Controller;
import org.liujiaxin.jaweb.mvc.annotation.RequestMapping;
import org.liujiaxin.jaweb.mvc.handler.Handler;
import org.liujiaxin.jaweb.mvc.handler.Interceptor;
import org.liujiaxin.jaweb.mvc.route.URI;
import org.liujiaxin.jaweb.mvc.view.JsonViewResolver;
import org.liujiaxin.jaweb.mvc.view.JspViewResolver;
import org.liujiaxin.jaweb.mvc.view.ViewResolver;

public class AppContext implements Context {

    private SimpleBeanFactory beanFactory;

    private AopHelper aopHelper;

    Map<URI, Handler> handlerMap;

    private Map<String, Interceptor> interceptorMap;

    private List<ViewResolver> viewResolvers;

    public List<ViewResolver> getViewResolvers() {
        return viewResolvers;
    }

    @Override
    public void init() {
        beanFactory = new DefaultBeanFactory();
        aopHelper = new AopHelper();
        beanFactory.init();
        initController(beanFactory);
        initInterceptor(beanFactory);
        initViewResolver();
    }

    private void initViewResolver() {
        this.viewResolvers = new ArrayList<ViewResolver>();
        viewResolvers.add(new JsonViewResolver());
        viewResolvers.add(new JspViewResolver());
    }

    private void initInterceptor(SimpleBeanFactory beanFactory) {
        interceptorMap = null;
    }

    private void initController(SimpleBeanFactory beanFactory) {
        handlerMap = new HashMap<URI, Handler>();

        DefaultBeanFactory defaultBeanFactory = (DefaultBeanFactory) beanFactory;
        Map<String, Object> beanMap = defaultBeanFactory.getBeans();
        for (Object obj : beanMap.keySet()) {
            Class<?> clazz = obj.getClass();
            if (clazz.getAnnotation(Controller.class) != null) {
                Method[] methods = clazz.getMethods();
                for (Method m : methods) {
                    RequestMapping requestMapping = m.getAnnotation(RequestMapping.class);
                    if (requestMapping != null) {
                        URI uri = new URI(requestMapping.value());
                        Handler handler = new Handler(obj, m, null);
                        handlerMap.put(uri, handler);
                    }
                }
            }
        }

    }

    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    public SimpleBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(SimpleBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public AopHelper getAopHelper() {
        return aopHelper;
    }

    public void setAopHelper(AopHelper aopHelper) {
        this.aopHelper = aopHelper;
    }

    public Map<URI, Handler> getHandlerMap() {
        return handlerMap;
    }

    public void setHandlerMap(Map<URI, Handler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public Map<String, Interceptor> getInterceptorMap() {
        return interceptorMap;
    }

    public void setInterceptorMap(Map<String, Interceptor> interceptorMap) {
        this.interceptorMap = interceptorMap;
    }


}
