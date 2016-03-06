package com.jason.em;

import java.lang.reflect.Method;

import com.jason.em.config.ConfigLoader;
import com.jason.em.ioc.Container;
import com.jason.em.ioc.impl.EmIoC;
import com.jason.em.render.JspRender;
import com.jason.em.render.Render;
import com.jason.em.route.Route;
import com.jason.em.route.Routers;

public class Em {
	
	private Routers routers;
	
	private Render render;
	
	private ConfigLoader configLoader;
	
	private Container ioc;
	
	private boolean init = false;
	
	private Em() {
		routers = new Routers();
		configLoader = new ConfigLoader();
		render = new JspRender();
		ioc = new EmIoC();
	}
	
	private static class EmHolder{
		private static Em em = new Em();
	}
	
	public static Em Me(){
		return EmHolder.em;
	}

	public Routers getRouters() {
		return routers;
	}

	public void setRouters(Routers routers) {
		this.routers = routers;
	}

	public Render getRender() {
		return render;
	}

	public void setRender(Render render) {
		this.render = render;
	}

	public ConfigLoader getConfigLoader() {
		return configLoader;
	}

	public void setConfigLoader(ConfigLoader configLoader) {
		this.configLoader = configLoader;
	}

	public boolean isInit() {
		return init;
	}

	public void setInit(boolean init) {
		this.init = init;
	}
	
	
	
	public Container getIoc() {
		return ioc;
	}

	public void setIoc(Container ioc) {
		this.ioc = ioc;
	}

	public void addRoute(String path , String methodName , Object controller){
		Route r = new Route();
		r.setPath(path);
		r.setController(controller);
		Class<?> clazz = controller.getClass();
		Method[] methods = clazz.getMethods();
		for(Method m : methods){
			if(methodName.equals(m.getName())){
				r.setAction(m);
				break;
			}
		}
		routers.addRoute(r);
		
	}

	
	

}
