package com.pugwoo.gson;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TestJsonObject {

	public static void main(String[] args) {
		
		String json = "{id:123,name:'pugwoo'}";
		
		Gson gson = new Gson();
		
		Type mapType = new TypeToken<Map<String,String>>() {
		}.getType();
		
		Map map = gson.fromJson(json,mapType);
		
		System.out.println(map.size());
		
		Set keys = map.keySet();
		Iterator iter = keys.iterator();
		while(iter.hasNext()){
			String key = (String)iter.next();
			System.out.println((String)map.get(key));
		}
	}

}
