package com.jason.em;

public class A  implements Person{
	private B partner;

	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public B getPartner() {
		return partner;
	}

	public void setPartner(B partner) {
		this.partner = partner;
	}

	public void say() {
		System.out.println("a is saying");
	}

	public void walk() {
		System.out.println("a is walking");
		
	}
}
