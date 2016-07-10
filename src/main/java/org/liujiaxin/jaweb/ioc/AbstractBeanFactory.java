package org.liujiaxin.jaweb.ioc;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBeanFactory implements SimpleBeanFactory {

    /*
     * com.xx.xx -- Object
     */
    private Map<String, Object> beans;

    /*
     * name -- com.xx.xx
     */
    private Map<String, String> beanKeys;

    public Map<String, Object> getBeans() {
        return beans;
    }

    public void setBeans(Map<String, Object> beans) {
        this.beans = beans;
    }

    public Map<String, String> getBeanKeys() {
        return beanKeys;
    }

    public void setBeanKeys(Map<String, String> beanKeys) {
        this.beanKeys = beanKeys;
    }

    public AbstractBeanFactory() {
        this.beans = new ConcurrentHashMap<String, Object>();
        this.beanKeys = new ConcurrentHashMap<String, String>();
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        String name = clazz.getName();
        Object o = beans.get(name);
        if (null != o) {
            return (T) o;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBeanByName(String name) {
        String className = beanKeys.get(name);
        Object obj = beans.get(className);
        if (null != obj) {
            return (T) obj;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBeanByClassName(String className) {
        Object obj = beans.get(className);
        if (null != obj) {
            return (T) obj;
        }
        return null;
    }

    public Object registerBean(Object bean) {
        String className = bean.getClass().getName();
        beans.put(className, bean);
        beanKeys.put(className, className);
        return bean;
    }

    public Object registerBean(Class<?> clazz) {
        String className = clazz.getName();
        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        beans.put(className, obj);
        beanKeys.put(className, className);
        return obj;
    }

    public Object registerBean(String name, Object bean) {
        String className = bean.getClass().getName();
        beans.put(className, bean);
        beanKeys.put(name, className);
        return bean;
    }

    public Object registerBean(String name, String className, Object bean) {
        beans.put(className, bean);
        beanKeys.put(name, className);
        return bean;
    }

    public void remove(Class<?> clazz) {
        String className = clazz.getName();
        if (null != className && !className.equals("")) {
            beanKeys.remove(className);
            beans.remove(className);
        }

    }

    public void removeByName(String name) {
        String className = beanKeys.get(name);
        if (null != className && !className.equals("")) {
            beanKeys.remove(className);
            beans.remove(className);
        }
    }

    public Set<String> getBeanNames() {
        return beanKeys.keySet();
    }

    public void initWired() {
        Iterator<Entry<String, Object>> it = beans.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
            Object object = entry.getValue();
            wired(object);
        }

    }

    private void wired(Object obj) {

    }

    public void initWired(Object object, Map<String, Object> prop) {
        Class<?> clazz = object.getClass();
        for (Map.Entry<String, Object> e : prop.entrySet()) {
            try {
                Field f = clazz.getDeclaredField(e.getKey());
                f.setAccessible(true);
                f.set(object, e.getValue());
            } catch (NoSuchFieldException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (SecurityException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IllegalArgumentException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    public abstract void init();

    public Object replaceBean(String classname, Object nobj) {
        beans.put(classname, nobj);
        return nobj;
    }

}
