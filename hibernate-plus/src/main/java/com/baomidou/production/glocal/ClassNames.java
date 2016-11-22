/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Caratacus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.baomidou.production.glocal;

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
