package com.jason.em.route;

import java.util.List;

public class RouteMatcher {

	private List<Route> routes;

	public RouteMatcher(List<Route> routes) {
		this.routes = routes;
	}

	public RouteMatcher() {
		
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	// 相对路径匹配
	public Route findRoute(String path) {
		for (Route r : routes) {
			if (r.getPath().matches(path)) {
				return r;
			}
		}
		return null;
	}

}
