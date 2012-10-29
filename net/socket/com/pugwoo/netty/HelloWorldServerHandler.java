package com.pugwoo.netty;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class HelloWorldServerHandler extends SimpleChannelUpstreamHandler {

	// @Override
	// public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent
	// e)
	// throws Exception {
	// // 一般通过e.getChannel()的write方法发送数据
	// // e.getChannel().write("Welcome!\n");
	// }

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		// 通过e.getMessage()获得数据,如果有decoder，这个是由decoder解码后返回的Object
		// if(!(e.getMessage() instanceof String))
		// return;

		String msg = (String) e.getMessage();
		e.getChannel().write(msg);

		// 注意：要用err才能向Console答应出信息
		// System.err.println(msg);

		// 为了测试，发送一次消息后就关闭
		e.getChannel().close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		e.getCause().printStackTrace();
		// 出错时关闭channel
		e.getChannel().close();
	}
}
