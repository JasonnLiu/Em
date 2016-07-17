Ja-Web
=================
一个基于Java,轻量级的Web框架。
>支持ioc,aop,mvc,orm的简单功能，xml和annotation两种配置。ioc的实现基于反射，aop通过动态代理实现，mvc参照Spring风格重新实现，支持json，jsp两种返回形式。集成Mybatis,通过类库和简单配置提高数据访问层的开发效率。

- mvc时序图

![](http://7xwciz.com1.z0.glb.clouddn.com/mvc_new.jpg)

- ioc容器接口
```java
    package com.jason.em.ioc;
    
    import java.util.Set;
    
    public interface SimpleBeanFactory {
    
    	public <T> T getBean(Class<T> clazz);
    
    	public <T> T getBeanByName(String name);
    	
    	public <T> T getBeanByClassName(String className);
    	
    	public Object registerBean(Object bean);
    	
    	public Object registerBean(Class<?> clazz);
    
    	public Object registerBean(String name,Object bean);
    	
    	public void remove(Class<?> clazz);
    	
    	public void removeByName(String name);
    	
    	public Object replaceBean(String classname,Object nobj);
    	
    	public Set<String> getBeanNames();
    	
    	public void initWired();
    	
    	public void init();
    }
```
- orm集成Mybatis，简化程序对数据库的操作，封装了底层SQL语句。凭借简单的配置和类库进一步提高开发效率。

```java
            SqlPageQuery sqlQueryForEntries = new SqlPageQuery(offset, pageSize);
            sqlQueryForEntries.whereEquals("Status", 0).whereLessThan("StartTime", time)
                              .whereGatherThanEquals("EndTime", time)
                              .order("OrderIndex", OrderRule.DESC)
                              .order("CreateTime", OrderRule.DESC);
            List<?> returnValue= SomeService.getValueByPageQuery(sqlQueryForEntries);
```

Mapper文件配置
```xml
<select id="" resultType="">
		<![CDATA[
			select * from Table
		]]>
		<foreach collection="sqlQuery.wheres" item="where" separator=" and "
			open=" where ">
			<choose>
				<when test="where.operation.inCase">
					<![CDATA[
						${where.column} ${where.operation.stringValue}
					]]>
					<foreach collection="where.value" item="inItem" open="("
						separator="," close=")">
						<![CDATA[
							#{inItem}
						]]>
					</foreach>
				</when>
				<otherwise>
					<![CDATA[
						${where.column} ${where.operation.stringValue} #{where.value}
					]]>
				</otherwise>
			</choose>
		</foreach>
		<foreach collection="sqlQuery.orders" item="order" separator=","
			open=" order by ">
			<![CDATA[
				${order.orderColumn} ${order.orderRule}
			]]>
		</foreach>
		<![CDATA[
			limit ${offset}, ${limit}
		]]>
	</select>
```


