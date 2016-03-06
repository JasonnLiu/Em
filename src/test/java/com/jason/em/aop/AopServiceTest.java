package com.jason.em.aop;

import org.junit.Test;

import com.jason.em.B;
import com.jason.em.C;
import com.jason.em.Person;
import com.jason.em.ioc.Container;
import com.jason.em.ioc.impl.EmIoC;

public class AopServiceTest {

	@Test
	public void test() {
		AopService aop = new AopService();
		Container ioc = new EmIoC();
		ioc.init();
		aop.setIoc(ioc);
		aop.init();
		
		Person p = (Person)ioc.getBeanByName("b");
		C c = (C)ioc.getBeanByName("c");
		p.say();
		p.walk();
		//b.say();
		
		
	}

}
