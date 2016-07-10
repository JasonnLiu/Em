package org.liujiaxin.jaweb.mvc.enumeration;

import org.liujiaxin.jaweb.util.enumeration.StringEnum;

public enum ResponseType implements StringEnum {

    JSP("jsp"),

    JSON("json"),

    OTHER("");

    private String stringValue;

    private ResponseType(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String getStringValue() {
        return this.stringValue;
    }

    @Override
    public boolean isNull() {
        return false;
    }

}
