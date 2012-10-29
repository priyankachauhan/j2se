package com.pugwoo.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试一下JSON处理的极限速度
 * 
 * @author Administrator
 * 
 */
public class Limit {

	public static void main(String[] args) {
		Long start;
		Long end;

		String json = "{\"id\":3,\"name\":\"pugwoo\",\"password\":\"123456789abcdefg\",\"age\":24,\"income\":1234.56789,\"others\":{\"birth\":\"1987-03-30\",\"male\":true,\"interest:\":[\"Computer\",3,\"math\"]}}";
		char[] jsonChars = json.toCharArray();

		// int[] LBRACKET = new int[json.length()];
		// int[] RBRACKET = new int[json.length()];
		// int[] LBRACE = new int[json.length()];
		// int[] RBRACE = new int[json.length()];
		// int[] COMMA = new int[json.length()];
		// int[] COLON = new int[json.length()];
		// int[] QUOTA = new int[json.length()];

		// int[][] position = new int[256][json.length()];
		// int[] count = new int[256];

		int[] position = new int[40960];
		char[] ch = new char[40960];
		int current;

		start = System.nanoTime();
		for (int i = 0; i < 100000; i++) {
			int length = json.length();
			current = 0;

			// int posLBRACKET = 0;
			// int posRBRACKET = 0;
			// int posLBRACE = 0;
			// int posRBRACE = 0;
			// int posCOMMA = 0;
			// int posCOLON = 0;
			// int posQUOTA = 0;

			Map<String, Object> map = new HashMap<String, Object>();

			for (int j = 0; j < length; j++) {
				char c = jsonChars[j];
				char d = 0;
				if (c == 123 || c == 125 || c == 133 || c == 135 || c == 58
						|| c == 44 || (c == 34 && d != 92)) {
					position[current] = j;
					ch[current++] = c;
				}
				d = c;

				// switch (c) {
				// case '"':
				// QUOTA[posQUOTA++] = j;
				// break;
				// case '{':
				// LBRACKET[posLBRACKET++] = j;
				// break;
				// case '}':
				// RBRACKET[posRBRACKET++] = j;
				// break;
				// case '[':
				// LBRACE[posLBRACE++] = j;
				// break;
				// case ']':
				// RBRACE[posRBRACE++] = j;
				// break;
				// case ',':
				// COMMA[posCOMMA++] = j;
				// break;
				// case ':':
				// COLON[posCOLON++] = j;
				// break;
				// default:
				// break;
				// }
			}

			// String id = json.substring(2, 6);
			// String name = json.substring(8, 15);
			String id = new String(jsonChars, 2, 2);
			String name = new String(jsonChars, 8, 7);

			// 直接生成对象，看需要多少时间
			map.put(id, Integer.valueOf("3"));
			map.put(name, "pugwoo");
			map.put("password", "123456789abcdefg");
			map.put("age", Integer.valueOf("24"));
			map.put("income", Double.valueOf("1234.56789"));
			Map<String, Object> others = new HashMap<String, Object>();
			others.put("birth", "1987-03-30");
			others.put("male", Boolean.valueOf("true"));
			List<Object> interest = new ArrayList<Object>();
			interest.add("Computer");
			interest.add(Integer.valueOf("3"));
			interest.add("math");
			others.put("interest", interest);
			map.put("others", others);
		}
		end = System.nanoTime();
		System.out.println("execute " + (end - start) / 1000000.0 + "ms.");
	}
}
