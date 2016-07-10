package org.liujiaxin.jaweb.orm.page;

import java.util.Collection;
import java.util.Map;

import org.liujiaxin.jaweb.orm.constant.WhereOperation;
import org.liujiaxin.jaweb.orm.sql.SqlQuery;



/**
 * @author hzjinxudong
 */
public class SqlPageQuery extends PageQuery {

    /**
     * 不提供set方法
     */
    private SqlQuery sqlQuery = new SqlQuery();

    public SqlPageQuery() {
    }

    public SqlPageQuery(int page) {
        super(page);
    }

    public SqlPageQuery(int page, int pageSize) {
        super(page, pageSize);
    }

    public SqlQuery getSqlQuery() {
        return sqlQuery;
    }

    public SqlPageQuery order(String orderColumn, Object orderRule) {
        sqlQuery.order(orderColumn, orderRule);
        return this;
    }

    public SqlPageQuery order(Collection<Map<String, Object>> collection) {
        for (Map<String, Object> item : collection) {
            String orderColumn = item.get("orderColumn").toString();
            Object orderRule = item.get("orderRule");
            order(orderColumn, orderRule);
        }
        return this;
    }

    public SqlPageQuery whereEquals(String column, Object value) {
        return where(column, value, WhereOperation.EQ);
    }

    public SqlPageQuery whereNotEquals(String column, Object value) {
        return where(column, value, WhereOperation.NOTEQ);
    }

    public SqlPageQuery whereLessThan(String column, Object value) {
        return where(column, value, WhereOperation.LT);
    }

    public SqlPageQuery whereLessThanEquals(String column, Object value) {
        return where(column, value, WhereOperation.LTE);
    }

    public SqlPageQuery whereGatherThan(String column, Object value) {
        return where(column, value, WhereOperation.GT);
    }

    public SqlPageQuery whereGatherThanEquals(String column, Object value) {
        return where(column, value, WhereOperation.GTE);
    }

    public SqlPageQuery whereLike(String column, Object value) {
        return where(column, value, WhereOperation.LIKE);
    }

    public SqlPageQuery whereIn(String column, Collection<?> value) {
        return where(column, value, WhereOperation.IN);
    }

    public SqlPageQuery whereNotIn(String column, Collection<?> value) {
        return where(column, value, WhereOperation.NOTIN);
    }

    public SqlPageQuery where(String column, Object value, WhereOperation operation) {
        sqlQuery.where(column, value, operation);
        return this;
    }

}
