package org.liujiaxin.jaweb.mvc.handler;

import org.liujiaxin.jaweb.mvc.enumeration.ResponseType;

public class ResponseInfo {

    private Object result;

    private ResponseType type;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

}
