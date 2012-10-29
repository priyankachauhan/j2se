package com.pugwoo.simulate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Internet消息包
 * 2012-01-09
 */
public class InternetMessage {

	// 信息发送时间戳
	public Date time;

	// 发送方
	public Integer src;

	// 接收方
	public Integer dst;

	// 消息正文内容
	public String content;

	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		StringBuilder sb = new StringBuilder(32);
		sb.append('[').append(df.format(time)).append(']');
		sb.append('[').append(src).append("-->").append(dst).append(']');
		sb.append('[').append(content).append(']');
		return sb.toString();
	}

}
