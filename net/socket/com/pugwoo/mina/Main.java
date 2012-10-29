package com.pugwoo.mina;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.DatagramAcceptor;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * 来自http://jim19770812.blogspot.com/2012/02/minaecho-server.html
 * @author Administrator
 *
 */
public class Main {
	private static final int TCP_PORT = 50012;
	private static final int UDP_PORT = 50013;

	public static void main(String[] args) throws Exception {
		SocketAcceptor tcp_acceptor = new NioSocketAcceptor();
		tcp_acceptor.setReuseAddress(true);
		DefaultIoFilterChainBuilder tcp_chain = tcp_acceptor.getFilterChain();
		tcp_chain.addLast("codec", new ProtocolCodecFilter(
				new TextLineCodecFactory()));
		tcp_acceptor.setHandler(new EchoHandler());
		tcp_acceptor.bind(new InetSocketAddress(TCP_PORT));
		System.out.println(String.format("在端口%d开始监听TCP通讯",
				new Object[] { TCP_PORT }));

		DatagramAcceptor udp_acceptor = new NioDatagramAcceptor();
		DefaultIoFilterChainBuilder udp_chain = udp_acceptor.getFilterChain();
		udp_chain.addLast("codec", new ProtocolCodecFilter(
				new TextLineCodecFactory()));
		udp_acceptor.setHandler(new EchoHandler());
		udp_acceptor.bind(new InetSocketAddress(UDP_PORT));
		System.out.println(String.format("在端口%d开始监听UDP通讯",
				new Object[] { UDP_PORT }));

		for (;;) {
			// System.out.println("R: " +
			// acceptor.getStatistics().getReadBytesThroughput() +", W: " +
			// acceptor.getStatistics().getWrittenBytesThroughput());
			Thread.sleep(3000);
		}
	}
}
