package org.liujiaxin.jaweb.orm.sql;

import java.io.Serializable;

import org.liujiaxin.jaweb.orm.constant.OrderRule;


public class Order implements Serializable {
    private static final long serialVersionUID = -4628966229656855916L;

    private String orderColumn;

    private OrderRule orderRule;

    public Order(String orderColumn, OrderRule orderRule) {
        this.orderColumn = orderColumn;
        this.orderRule = orderRule;
    }

    public OrderRule getOrderRule() {
        return orderRule;
    }

    public void setOrderRule(OrderRule orderRule) {
        this.orderRule = orderRule;
    }

    public void setOrderRule(String orderRule) {
        this.orderRule = OrderRule.parse(orderRule, OrderRule.ASC);
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }
}
