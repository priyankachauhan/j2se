package com.pugwoo.jackson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class GetStart {

	public static void main(String[] args) throws Exception {
		//deserialize();
		//deserialize2();
		String json = "\"\\\"23\"";
		ObjectMapper mapper = new ObjectMapper();
		Object obj = mapper.readValue(json, String.class);
		System.out.println(obj);
//		System.out.println(obj.getClass());
	}
	
	//jackson能支持把""转换成Long的null值，Gson不支持这个
	public static void deserialize() throws JsonParseException, JsonMappingException, IOException{
		String json = "{\"id\":\"\",\"name\":\"pugwoo\"}"; //注意，jackson只支持双括号
		ObjectMapper mapper = new ObjectMapper();
		User user = mapper.readValue(json, User.class);
		System.out.println(user);
	}
	
	/**
	 * 看能不能通过定义一个类实现我写的MyJson的功能，可以
	 */
	public static void deserialize2() throws Exception{
		String json = "{\"id\":3.3,\"name\":\"pugwoo\",\"school.name\":\"sysu\"}";
		ObjectMapper mapper = new ObjectMapper();
		Map map = mapper.readValue(json, HashMap.class);
		System.out.println(map);
		System.out.println(((Object)map.get("id")).getClass());
	}

}
