package org.liujiaxin.jaweb.orm.constant;

import org.apache.commons.lang3.StringUtils;
import org.liujiaxin.jaweb.util.enumeration.StringEnum;


public enum OrderRule implements StringEnum {

    DESC("DESC"),

    ASC("ASC");

    private OrderRule(String stringValue) {
        this.stringValue = stringValue;
    }

    private String stringValue;

    public String getStringValue() {
        return stringValue;
    }

    public boolean isNull() {
        return false;
    }

    public static OrderRule parse(String stringValue, OrderRule defaultValue) {
        for (OrderRule orderRule : values()) {
            if (StringUtils.equalsIgnoreCase(orderRule.getStringValue(), stringValue)) {
                return orderRule;
            }
        }
        return defaultValue;
    }

}
