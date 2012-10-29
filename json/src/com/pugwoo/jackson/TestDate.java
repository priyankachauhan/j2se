package com.pugwoo.jackson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * 这种方法其实不太好，不过能work
 * 
 * 还有一种是写一个deserialize，当对于每个date变量都必须指定使用该deserialize类，不是轻量级
 * 
 * 还有一种是配置objectMapper：http://stackoverflow.com/questions/4428109/jersey-jackson-json-date-serialization-format-problem-how-to-change-the-forma
 * 这种是轻量级的
 * 
 * http://java.dzone.com/articles/how-serialize-javautildate
 * @author Administrator
 *
 */
public class TestDate {

	public static ObjectMapper objectMapper = new ObjectMapper();
	
	public static void main(String[] args) throws Exception{
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = df.parse("2011-04-03T08:00:00");
		
		System.out.println(date);
		
		String json = objectMapper.writeValueAsString(df.format(date));
		
		System.out.println(json);

	}

}
