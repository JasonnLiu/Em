package org.liujiaxin.jaweb.orm.sql;

import java.io.Serializable;

import org.liujiaxin.jaweb.orm.constant.WhereOperation;


public class Where implements Serializable {
    private static final long serialVersionUID = 3497009860993579666L;

    private String column;

    private Object value;

    private WhereOperation operation;

    public Where(String column, Object value, WhereOperation operation) {
        this.column = column;
        this.value = value;
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public WhereOperation getOperation() {
        return operation;
    }

    public void setOperation(WhereOperation operation) {
        this.operation = operation;
    }

}
