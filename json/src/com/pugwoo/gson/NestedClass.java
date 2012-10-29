package com.pugwoo.gson;

import java.util.Date;

import com.google.gson.Gson;

public class NestedClass {

	//如果是inner class则不能这么做
	public static void main(String args[]){
		School school = new School();
		User user = new User();
		
		user.setId(123);
		user.setName("huang");
		user.setBirth(new Date());
		
		school.setName("sysu");
		school.setPresident(user);
		
		System.out.println(new Gson().toJson(school));
	}
}
