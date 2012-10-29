package com.pugwoo.gson;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ListJson {

	public static void main(String[] args) {
		String json = "[{id:1234,name:'nick',birth:'2011-1-21 15:16:01'},"
				+ "{id:1235,name:'awoo',birth:'2011-1-24 15:17:01'},"
				+ "{id:1236,name:'karen'}]";

		// 下面这一段也行
		// User[] users = new Gson().fromJson(json, User[].class);
		// for(int i=0; i<users.length; i++){
		// System.out.println(users[i].getName());
		// }

		Type listType = new TypeToken<List<User>>() {
		}.getType();
		List<User> users = new Gson().fromJson(json, listType);
		for (int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i).getName());
		}

		// toJson Again
		System.out.println(new Gson().toJson(users));

	}

}
