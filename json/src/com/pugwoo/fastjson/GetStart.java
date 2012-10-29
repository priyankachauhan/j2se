package com.pugwoo.fastjson;

import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;


public class GetStart {

	public static Object getNumberValue(String str) {
		if(str == null) return null;
		Object object = null;
		try {
			object = Long.parseLong(str);
		} catch (Exception e) {
			try {
				object = Double.parseDouble(str);
			} catch (Exception ex) {
			}
		}
		return object == null ? str : object;
	}
	
	public static void main(String[] args) {
		
		String json = "{\"id\":\"3\",\"name\":\"pugwoo\",\"age\":''}";
		
		Map map = (Map) JSON.parse(json);
		
		System.out.println(map);
		
		/**
		 * 日期
		 */
		SerializeWriter out = new SerializeWriter();

		JSONSerializer serializer = new JSONSerializer(out);
		serializer.config(SerializerFeature.UseISO8601DateFormat, true);
		serializer.write(new Date());

		System.out.println(out.toString());
		
		//String json = "{\"id\":\"3\",\"name\":\"pugwoo\",\"age\":''}";
	//String json = "-6470204979932713723 ";
		//String json = "{id:'3',age:''}";
		
		//User u = JSON.parseObject(json, User.class);
		//JSON.parseObject("534", Object.class);
	//	getNumberValue("fewer");
	//	Long start = System.nanoTime();
	//	Object i = JSON.parseObject("[53564,79798,798]", Object.class);
	//	i = JSON.parseObject("[53564,79798,798]", Object.class);
	//	Object i = getNumberValue("123");//i = getNumberValue("12");
	//	Long end = System.nanoTime();
	//	System.out.println("execute " + (end - start) / 1000000.0 + "ms.");

	//	System.out.println(i.getClass());
	}

}
