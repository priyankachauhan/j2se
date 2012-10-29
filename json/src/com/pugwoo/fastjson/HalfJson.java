package com.pugwoo.fastjson;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class HalfJson {

	public static void main(String[] args) {
		Long start;
		Long end;

		/**
		 * Map测试
		 */
		String json = "{\"id\":3,\"name\":\"pugwoo\",\"password\":\"123456789abcdefg\",\"age\":24,\"income\":1234.56789,\"others\":{\"birth\":\"1987-03-30\",\"male\":true,\"interest:\":[\"Computer\",3,\"math\"]}}";
		Map map = null;
		start = System.nanoTime();
		for (int i = 0; i < 100000; i++)
			map = (Map) JSON.parse(json);
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
			list = (List) JSON.parse(json);
		end = System.nanoTime();
		System.out.println("execute " + (end - start) / 1000000.0 + "ms.");
		System.out.println(list);
		
		/**
		 * Single测试
		 */
		json = "34";
		Object obj = null;
		obj = JSON.parse(json);
		System.out.println(obj);
		System.out.println(obj.getClass());
	}

}
