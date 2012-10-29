package com.pugwoo.myjson;

public class HalfJson {

	public static void main(String[] args) {
		Long start;
		Long end;

		/**
		 * Map测试,太慢了
		 */
		String json = "{\"id\":3,\"name\":\"pugwoo\",\"password\":\"123456789abcdefg\",\"age\":24,\"income\":1234.56789,\"others\":{\"birth\":\"1987-03-30\",\"male\":true,\"interest:\":[\"Computer\",3,\"math\"]}}";
		MyJson myjson = null;
		start = System.nanoTime();
		for (int i = 0; i < 100000; i++)
			myjson = new MyJson(json);
		end = System.nanoTime();
		System.out.println("execute " + (end - start) / 1000000.0 + "ms.");

		System.out.println(myjson);
	}

}
