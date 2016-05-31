package com.jason.em.bootstrap;

import javax.servlet.ServletContext;

import com.jason.em.Em;

public interface Bootstrap {
	public void init(Em em);
	public void setContext(ServletContext context);
}
