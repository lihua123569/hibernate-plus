package com.app.hibernate.production.glocal;

public enum ClassNames {
	VO(1, "vo", ".java"), PO(2, "po", ".java"),CONTROLLER(3, "controller", "Controller.java"),SERVICE(4, "service", "Service.java"),SERVICEIMPL(5, "service.impl", "ServiceImpl.java");

	ClassNames(int index, String pakName, String methodName) {
		this.val = index;
		this.pakName = pakName;
		this.methodName = methodName;
	}

	public int getVal() {
		return this.val;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public String getPakName() {
		return this.pakName;
	}

	private int val;
	private String pakName;
	private String methodName;
}
