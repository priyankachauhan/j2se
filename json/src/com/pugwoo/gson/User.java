package com.pugwoo.gson;

import java.util.Date;

public class User {

	private int id;
	private String name;
	private Date birth;
	private transient int age; // 这个不会加到json中

	// 可以不用写Getter和Setter方法

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
