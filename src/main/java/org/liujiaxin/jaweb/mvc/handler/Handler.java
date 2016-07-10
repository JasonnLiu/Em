package org.liujiaxin.jaweb.mvc.handler;

import java.lang.reflect.Method;

import org.liujiaxin.jaweb.mvc.enumeration.ResponseType;

public class Handler {

    private Object instance;

    private Method method;

    private Object[] args;

    private ResponseType type;

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Handler(Object instance, Method method, Object[] args) {
        super();
        this.args = args;
        this.instance = instance;
        this.method = method;
    }

    public ResponseInfo execute() {
        ResponseInfo ri = null;
        try {
            ri = new ResponseInfo();
            ri.setType(this.type);
            Object result = null;
            result = method.invoke(instance, args);
            ri.setResult(result);
            return ri;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ri;
    }
}
