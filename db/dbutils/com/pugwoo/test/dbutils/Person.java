package com.pugwoo.test.dbutils;

/**

CREATE TABLE person ( 
     id bigint(20) NOT NULL AUTO_INCREMENT, 
     name varchar(24) DEFAULT NULL, 
     age int(11) DEFAULT NULL, 
     address varchar(120) DEFAULT NULL, 
     PRIMARY KEY (id) 
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=gbk;

通过getter和setter获得和设置数据
 * @author pugwoo
 * @date 2011-11-13
 */
public class Person {

	private Long id;
	private String name;
	private Integer age;
	private String address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
