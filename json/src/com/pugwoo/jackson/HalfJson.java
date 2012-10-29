package com.pugwoo.jackson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class HalfJson {

	public static void main(String[] args) throws JsonParseException,
			JsonMappingException, IOException {
		Long start;
		Long end;

		ObjectMapper objectMapper = new ObjectMapper();

		/**
		 * Map测试
		 */
		String json = "{\"id\":3,\"name\":\"pugwoo\",\"password\":\"123456789abcdefg\",\"age\":24,\"income\":1234.56789,\"others\":{\"birth\":\"1987-03-30\",\"male\":true,\"interest:\":[\"Computer\",3,\"math\"]}}";
		// 不支持String json =
		// "{id:3,name:'pugwoo',password:'123456789abcdefg',age:24,income:1234.56789,others:{birth:'1987-03-30',male:true,interest:['Computer',3,'math']}}";

		Map map = null;
		start = System.nanoTime();
		for (int i = 0; i < 100000; i++)
			map = (Map) objectMapper.readValue(json, Object.class);
		end = System.nanoTime();
		System.out.println("execute " + (end - start) / 1000000.0 + "ms.");
		System.out.println(map);

		/**
		 * List测试
		 */
		json = "[\"hi\",123,123.4545,[],[\"google\",{},{\"name\":\"g\",\"age\":28}]]";
		List list = null;
		start = System.nanoTime();
		for (int i = 0; i < 100000; i++)
			list = (List) objectMapper.readValue(json, Object.class);
		end = System.nanoTime();
		System.out.println("execute " + (end - start) / 1000000.0 + "ms.");
		System.out.println(list);
	}

}
