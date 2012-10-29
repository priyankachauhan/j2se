package com.pugwoo.gson;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

/**
 * 常用方法：
 * new Gson().toJson(Object obj); //将obj转换成json
 * new Gson().fromJson(String json, Class clazz); //将json转变成Object
 */
@SuppressWarnings("unused")
public class GetStart {

	public static void main(String[] args) {

		//Primitives Examples
		Gson gson = new Gson();
		//(Serialization)
		System.out.println(gson.toJson(1));
		System.out.println(gson.toJson("abcd"));
		System.out.println(gson.toJson(new Long(10)));
		System.out.println(gson.toJson(new int[]{1,2,3,4}));
		
		Set set = new HashSet();
		set.add("set1"); set.add("set2");
		System.out.println(gson.toJson(set));
		
		Map map = new HashMap();
		map.put("mapkey1", "mapvalue1");
		map.put("mapkey2", 8888);
		System.out.println(gson.toJson(map));
		
		//(Deserialization)
		int intValue = gson.fromJson("1", int.class);
		Integer integer = gson.fromJson("1", Integer.class);
		Long longValue = gson.fromJson("1", Long.class);
		Boolean bool = gson.fromJson("false", Boolean.class);
		String str = gson.fromJson("\"abc\"", String.class);
		String anotherStr = gson.fromJson("[\"abc\"]", String.class);
		
		//Object Examples
		//注意，如果Ueser是继承自父类，则json也会产生父类的数据
		User user = new User();
		user.setId(23);
		user.setName("pugwoo");
		user.setBirth(new Date());
		user.setAge(19);
		String json = new Gson().toJson(user);
		System.out.println(json);
		
		//(Deserialization)
		User user1 = new Gson().fromJson(json, User.class);
		System.out.println("get user from json: name="+user1.getName());
		
		//more examples, see http://sites.google.com/site/gson/gson-user-guide
	}

}
