Em
=================
一个基于Java,轻量级的Web框架。
>支持mvc,ioc,aop的简单功能，xml和annotation两种配置。

- mvc时序图

- ioc容器接口
```java
    package com.jason.em.ioc;
    
    import java.util.Set;
    
    public interface Container {
    
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
