package org.liujiaxin.jaweb.orm.constant;

import org.liujiaxin.jaweb.util.enumeration.StringEnum;

/**
 * @author baizhichao
 */
public enum WhereOperation implements StringEnum {
    EQ("=", false),

    NOTEQ("!=", false),

    LT("<", false),

    LTE("<=", false),

    GT(">", false),

    GTE(">=", false),

    LIKE("like", false),

    IN("in", true),

    NOTIN("not in", true);

    private WhereOperation(String stringValue, boolean inCase) {
        this.stringValue = stringValue;
        this.inCase = inCase;
    }

    private String stringValue;

    private boolean inCase;

    public String getStringValue() {
        return stringValue;
    }

    public boolean isInCase() {
        return inCase;
    }

    public boolean isNull() {
        return false;
    }

}
