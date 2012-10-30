package com.flyoung.xmlrpc;

public class HelloHandler implements ServicesHandler {

	public String execute(String str) {
		return "hello " + str + "!";
	}

}