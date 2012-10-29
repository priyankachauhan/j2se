package com.pugwoo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 对JAVA自带的Date、DateFormat、Calendar等的封装
 * 设计：提供String的输入输出格式，包括【自身输入输出格式】和【全局输入输出格式】
 * 
 * @author pugwoo
 * @date 2011年9月19日 上午12:05:18 创建
 * @date 2011-12-15 23:07 新增写入、日期计算
 */
@SuppressWarnings("serial")
public class Date extends java.util.Date {

	// 单元测试
	public static void main(String args[]) throws ParseException {
		Date date = new Date("2000-11-11 11:11:11");
		System.out.println(date);

		date.setOutputDateFormat(Date.CHINESE_DATE_FORMAT);
		System.out.println(date);
		System.out.println(date.format(Date.MYSQL_DATETIME_FORMAT));

		Date date2 = new Date("2011年11月11日", "yyyy年MM月dd日");
		System.out.println(date2);

		Date date4 = new Date(2011, 1, 2, 3, 4, 5, 6);
		System.out.println(date4.format(MYSQL_DATETIME_FORMAT));
		
		System.out.println(date4.getMonth());

	}

	// 各种日期时间格式

	public final static String MYSQL_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	public final static String CHINESE_DATE_FORMAT = "yyyy年MM月dd日";

	public final static String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public final static String DATE_FORMAT = "yyyy-MM-dd";

	public final static String TIME_FORMAT = "HH:mm:ss";

	// 日期输入格式
	private String inputDateFormat;

	// 日期输出格式
	private String outputDateFormat;

	// 全局日期输入格式
	private static String globalInputDateFormat = STANDARD_FORMAT;

	// 全局日期输出格式
	private static String globalOutputDateFormat = STANDARD_FORMAT;

	// Calendar对象
	private Calendar calendar;

	// Calendar对象用到时才加载
	private Calendar getCalendar() {
		if (calendar == null) {
			calendar = Calendar.getInstance();
		}
		calendar.setTime(this);
		return calendar;
	}

	public Date() {
		super();
	}

	public Date(long time) {
		super(time);
	}

	public Date(java.util.Date date) {
		super(date.getTime());
	}

	public Date(String time) {
		super(input(time, null).getTime());
	}

	/**
	 * 输入输出格式都会被设置为pattern
	 */
	public Date(String time, String pattern) {
		super(input(time, pattern).getTime());
		inputDateFormat = pattern;
		outputDateFormat = pattern;
	}

	/**
	 * @param month
	 *            月份从1开始，不同于Calendar或java.util.Date
	 */
	public Date(int year, int month, int date) {
		input(year, month, date, 0, 0, 0, 0);
	}

	/**
	 * @param month
	 *            月份从1开始，不同于Calendar或java.util.Date
	 */
	public Date(int year, int month, int date, int hour, int minute) {
		input(year, month, date, hour, minute, 0, 0);
	}

	/**
	 * @param month
	 *            月份从1开始，不同于Calendar或java.util.Date
	 */
	public Date(int year, int month, int date, int hour, int minute, int second) {
		input(year, month, date, hour, minute, second, 0);
	}

	/**
	 * @param month
	 *            月份从1开始，不同于Calendar或java.util.Date
	 */
	public Date(int year, int month, int date, int hour, int minute,
			int second, int millisecond) {
		input(year, month, date, hour, minute, second, millisecond);
	}

	/**
	 * 优先级顺序：对象自身、全局、父类
	 */
	@Override
	public String toString() {
		return output(this, outputDateFormat);
	}

	/**
	 * 按照给定的格式输出String
	 * 
	 * @param pattern
	 *            格式
	 * @return
	 */
	public String format(String pattern) {
		return output(this, pattern);
	}

	/**
	 * pattern或者全局pattern必须至少一个有值，否则按标准格式来转换
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	private static Date input(String time, String pattern) {
		if (time == null) {
			return null;
		}
		try {
			if (pattern == null) {
				pattern = globalInputDateFormat != null ? globalInputDateFormat
						: STANDARD_FORMAT;
			}
			return new Date(new SimpleDateFormat(pattern).parse(time).getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param month
	 *            月份从1开始
	 */
	private void input(int year, int month, int date, int hour, int minute,
			int second, int millisecond) {
		calendar = getCalendar();
		calendar.clear();
		calendar.set(year, month - 1, date, hour, minute, second);
		calendar.set(Calendar.MILLISECOND, millisecond);
		setTime(calendar.getTimeInMillis());
	}

	/**
	 * 如果pattern为null，则采用全局输出格式
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	private static String output(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		try {
			if (pattern == null) {
				pattern = globalOutputDateFormat != null ? globalOutputDateFormat
						: STANDARD_FORMAT;
			}
			return new SimpleDateFormat(pattern).format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 提供日期的增减功能

	/**
	 * @param field
	 *            对应于Calendar定义的域
	 */
	public void add(int field, int num) {
		Calendar cal = getCalendar();
		cal.add(field, num);
		setTime(cal.getTimeInMillis());
	}

	// 获取各个域
	
	public int getYear() {
		return getCalendar().get(Calendar.YEAR);
	}

	public int getMonth() {
		return getCalendar().get(Calendar.MONTH) + 1;
	}

	public int getDayOfMonth() {
		return getCalendar().get(Calendar.DAY_OF_MONTH);
	}

	public int getDayOfWeek() {
		return getCalendar().get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 24小时制
	 */
	public int getHours() {
		return getCalendar().get(Calendar.HOUR_OF_DAY);
	}

	public int getMinutes() {
		return getCalendar().get(Calendar.MINUTE);
	}

	public int getSeconds() {
		return getCalendar().get(Calendar.SECOND);
	}
	
	public int getMilliSeconds() {
		return getCalendar().get(Calendar.MILLISECOND);
	}
	
	public void setYear(int year) {
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, year);
		setTime(cal.getTimeInMillis());
	}

	// 下面是getter和setter方法

	public String getOutputDateFormat() {
		return outputDateFormat;
	}

	public void setOutputDateFormat(String outputDateFormat) {
		this.outputDateFormat = outputDateFormat;
	}

	public String getInputDateFormat() {
		return inputDateFormat;
	}

	public void setInputDateFormat(String inputDateFormat) {
		this.inputDateFormat = inputDateFormat;
	}

	public static String getGlobalInputDateFormat() {
		return globalInputDateFormat;
	}

	public static void setGlobalInputDateFormat(String globalInputDateFormat) {
		Date.globalInputDateFormat = globalInputDateFormat;
	}

	public static String getGlobalOutputDateFormat() {
		return globalOutputDateFormat;
	}

	public static void setGlobalOutputDateFormat(String globalOutputDateFormat) {
		Date.globalOutputDateFormat = globalOutputDateFormat;
	}

}
