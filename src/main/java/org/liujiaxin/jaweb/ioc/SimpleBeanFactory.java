package org.liujiaxin.jaweb.ioc;

import java.util.Set;

public interface SimpleBeanFactory {

    public <T> T getBean(Class<T> clazz);

    public <T> T getBeanByName(String name);

    public <T> T getBeanByClassName(String className);

    public Object registerBean(Object bean);

    public Object registerBean(Class<?> clazz);

    public Object registerBean(String name, Object bean);

    public void remove(Class<?> clazz);

    public void removeByName(String name);

    public Object replaceBean(String classname, Object nobj);

    public Set<String> getBeanNames();

    public void initWired();

    public void init();

}
