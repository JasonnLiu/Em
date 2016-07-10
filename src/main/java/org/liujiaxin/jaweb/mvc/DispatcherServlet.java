package org.liujiaxin.jaweb.mvc;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.liujiaxin.jaweb.context.AppContext;
import org.liujiaxin.jaweb.context.Context;
import org.liujiaxin.jaweb.context.ContextUtil;
import org.liujiaxin.jaweb.mvc.handler.ExecutionChain;
import org.liujiaxin.jaweb.mvc.handler.Handler;
import org.liujiaxin.jaweb.mvc.handler.Interceptor;
import org.liujiaxin.jaweb.mvc.handler.ResponseInfo;
import org.liujiaxin.jaweb.mvc.handler.TypeConverterFactory;
import org.liujiaxin.jaweb.mvc.route.URI;
import org.liujiaxin.jaweb.mvc.servlet.wrapper.DefaultRequest;
import org.liujiaxin.jaweb.mvc.servlet.wrapper.DefaultResponse;
import org.liujiaxin.jaweb.mvc.view.ViewResolver;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 8131714961162065701L;

    private ServletContext servletContext;

    private Context context = ContextUtil.getContext();

    private Map<String, Interceptor> interceptorMap;

    private Map<URI, Handler> handlerMap;

    private List<ViewResolver> viewResolvers;



    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DefaultRequest request = new DefaultRequest(req);
        DefaultResponse response = new DefaultResponse(resp);

        doDispatch(request, response);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        init(context);
    }

    private void init(Context context) {
        context.init();
        this.handlerMap = ((AppContext) context).getHandlerMap();
        this.interceptorMap = ((AppContext) context).getInterceptorMap();
        this.viewResolvers = ((AppContext) context).getViewResolvers();
    }

    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ExecutionChain executionChain = null;
        executionChain = getExecutionChain(request);
        if (!executionChain.applyPreHandle(request, response)) {
            return;
        }
        ResponseInfo ri = null;
        Handler handler = executionChain.getHandler();
        ri = handler.execute();
        executionChain.applyPostHandle(request, response, ri);
        processDispatchResult(request, response, ri);

    }

    private void processDispatchResult(HttpServletRequest request, HttpServletResponse response, ResponseInfo ri) {
        for (ViewResolver vr : viewResolvers) {
            if (vr.support(ri.getType())) {
                vr.resolveView(request, response, ri);
            }
        }
    }

    private ExecutionChain getExecutionChain(HttpServletRequest request) throws ServletException {
        String url = request.getServletPath();
        URI uri = new URI(url);
        if (!handlerMap.containsKey(uri)) {
            throw new ServletException("no handler mapping");
        }
        Handler handler = handlerMap.get(uri);
        Method method = handler.getMethod();
        Object[] args = null;
        Map<String, String[]> parameters_name_args = request.getParameterMap();
        if (parameters_name_args.size() != 0) {
            Class<?>[] clazz = method.getParameterTypes();
            List<String> ParametersName;
            try {
                ParametersName = getMethodParametersName(method);
            } catch (Exception e1) {
                throw new ServletException(e1);
            }
            args = new Object[clazz.length];
            for (int i = 0; i < ParametersName.size(); i++) {
                String name = ParametersName.get(i);
                String arg = (String) parameters_name_args.get(name)[0];
                if (clazz[i].equals(String.class)) {
                    args[i] = arg;
                } else {
                    try {
                        args[i] = TypeConverterFactory.convert(clazz[i], args);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }
        handler.setArgs(args);
        List<Interceptor> interceptors = uri.getMatchedInterceptor(interceptorMap);
        ExecutionChain executionChain = new ExecutionChain(handler, interceptors);
        return executionChain;
    }

    private List<String> getMethodParametersName(Method method) throws Exception {
        List<String> nameList = new LinkedList<String>();

        try {
            Class<?> clazz = method.getDeclaringClass();
            String methodName = method.getName();
            ClassPool pool = ClassPool.getDefault();
            pool.insertClassPath(new ClassClassPath(clazz));
            CtClass cc = pool.get(clazz.getName());
            CtMethod cm = cc.getDeclaredMethod(methodName);
            MethodInfo methodInfo = cm.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr =
                (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            String[] paramNames = new String[cm.getParameterTypes().length];
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
            for (int i = 0; i < paramNames.length; i++)
                nameList.add(attr.variableName(i + pos));
        } catch (Exception e) {
            throw e;
        }

        return nameList;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Map<String, Interceptor> getInterceptorMap() {
        return interceptorMap;
    }

    public void setInterceptorMap(Map<String, Interceptor> interceptorMap) {
        this.interceptorMap = interceptorMap;
    }

    public Map<URI, Handler> getHandlerMap() {
        return handlerMap;
    }

    public void setHandlerMap(Map<URI, Handler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public List<ViewResolver> getViewResolvers() {
        return viewResolvers;
    }

    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }
}
