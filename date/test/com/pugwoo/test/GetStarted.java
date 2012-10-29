package com.pugwoo.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 2011年4月13日 下午05:55:48
 */
public class GetStarted {

	public static void main(String[] args) throws ParseException {

		// 当前时间
		Date now = new Date();
		System.out.println(now);

		// 从String获得Date
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = dateFormat.parse("2011-04-03T09:00:00");
		System.out.println(date);

		// 通过Date获得Calendar
		Calendar calendar = Calendar.getInstance();

		// 对Calendar进行日期的运算
		calendar.add(Calendar.DATE, 7);

		// 获得Date
		date = calendar.getTime();
		System.out.println(date);
	}

}
