package com.jason.em.route;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;



public class Routers {

	private List<Route> routes = new ArrayList<Route>();

	public List<Route> getList() {
		return routes;
	}

	public void setList(List<Route> list) {
		this.routes = list;
	}

	public void addRoute(List<Route> l) {
		routes.addAll(l);
	}

	public void addRoute(Route r) {
		routes.add(r);
	}
	
	public void addRoute(String path,Method action,Object controller){
		Route r = new Route();
		r.setAction(action);
		r.setController(controller);
		r.setPath(path);
		routes.add(r);
		
	}

	public void remove(Route r) {
		routes.remove(r);
	}

}
