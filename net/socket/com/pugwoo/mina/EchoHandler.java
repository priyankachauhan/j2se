package com.pugwoo.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class EchoHandler extends IoHandlerAdapter {
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		System.out.println(String.format("session[%d] 有错误发生：%s", new
		 Object[]{session.getId(), cause}));
		session.close(true);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		System.out.println(String.format("session[%d] ->%s", new Object[] {
				session.getId(), message }));
		session.write(message);
		session.close(true); //add by pugwoo 第一个信息收到就关闭掉
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println(String.format("session[%d] 已关闭",
				new Object[] { session.getId() }));
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println(String.format("session[%d] 被创建",
				new Object[] { session.getId() }));
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println(String.format("session[%d] 处于空闲",
				new Object[] { session.getId() }));
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println(String.format("session[%d] 被打开",
				new Object[] { session.getId() }));
	}
}
