package org.liujiaxin.jaweb.orm.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.liujiaxin.jaweb.orm.constant.OrderRule;
import org.liujiaxin.jaweb.orm.constant.WhereOperation;


public class SqlQuery {

    private List<Where> wheres = new ArrayList<Where>();

    private List<Order> orders = new ArrayList<Order>();

    public SqlQuery order(String orderColumn, Object orderRule) {
        if (orderRule instanceof OrderRule) {
            orders.add(new Order(orderColumn, (OrderRule) orderRule));
        } else {
            orders.add(new Order(orderColumn, OrderRule.parse(orderRule.toString(), OrderRule.ASC)));
        }
        return this;
    }

    public SqlQuery order(Collection<Map<String, Object>> collection) {
        for (Map<String, Object> item : collection) {
            String orderColumn = item.get("orderColumn").toString();
            Object orderRule = item.get("orderRule");
            order(orderColumn, orderRule);
        }
        return this;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public SqlQuery whereEquals(String column, Object value) {
        return where(column, value, WhereOperation.EQ);
    }

    public SqlQuery whereNotEquals(String column, Object value) {
        return where(column, value, WhereOperation.NOTEQ);
    }

    public SqlQuery whereLessThan(String column, Object value) {
        return where(column, value, WhereOperation.LT);
    }

    public SqlQuery whereLessThanEquals(String column, Object value) {
        return where(column, value, WhereOperation.LTE);
    }

    public SqlQuery whereGatherThan(String column, Object value) {
        return where(column, value, WhereOperation.GT);
    }

    public SqlQuery whereGatherThanEquals(String column, Object value) {
        return where(column, value, WhereOperation.GTE);
    }

    public SqlQuery whereLike(String column, Object value) {
        return where(column, value, WhereOperation.LIKE);
    }

    public SqlQuery whereIn(String column, Collection<?> value) {
        return where(column, value, WhereOperation.IN);
    }

    public SqlQuery whereNotIn(String column, Collection<?> value) {
        return where(column, value, WhereOperation.NOTIN);
    }

    public SqlQuery where(String column, Object value, WhereOperation operation) {
        wheres.add(new Where(column, value, operation));
        return this;
    }

    public List<Where> getWheres() {
        return this.wheres;
    }

}
