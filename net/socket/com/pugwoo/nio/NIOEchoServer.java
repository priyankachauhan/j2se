package com.pugwoo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 2012年2月23日 21:28:16
 * 
 * @author Pugwoo
 * 
 *         用户输入一个字符串，返回该字符串的大写形式。
 *         !! 接受一个字符串返回后自动关闭连接（为了jmeter测试）
 */
public class NIOEchoServer {

	private Selector selector;

	public NIOEchoServer(int port) throws IOException {
		// 获得server的socketChannel
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.socket().bind(new InetSocketAddress(port));

		// 通过open()方法找到Selector，默认在JDK6+Linux2.6+上是用Epoll模型
		selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		System.out.println("Server started successfully at port: " + port);
	}

	// 监听，不停地接受客户端连接和处理读写事件
	public void listen() throws IOException {
		while (true) {
			// 阻塞，直到有事件发生
			selector.select();
			// 返回此选择器的已选择键集。
			Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
			while (keyIter.hasNext()) {
				SelectionKey selectionKey = keyIter.next();
				// 一定要删除，否则selector会陷入无尽的循环中，或者其它错误
				keyIter.remove();
				// 连接新Client并加入监听
				if (selectionKey.isAcceptable()) {
					ServerSocketChannel server = (ServerSocketChannel) selectionKey
							.channel();
					SocketChannel client = server.accept();
					// client可能为null(如果忘记设置server为非阻塞时)
					if (client != null) {
						System.out.println("new client connected: "
								+ client.socket().getRemoteSocketAddress());
						client.configureBlocking(false);
						// 注册到selector，等待数据
						client.register(selector, SelectionKey.OP_READ);
					}
				} else {
					handleClient(selectionKey);
				}
			}
		}
	}

	// 这个仅供给handleClient使用，多线程handleClient可能需要多个buffer
	private ByteBuffer buffer = ByteBuffer.allocate(1024);
	// 发送信息
	private HashMap<SocketChannel, String> toSend = new HashMap<SocketChannel, String>();

	// 处理client的读写操作
	protected void handleClient(SelectionKey selectionKey) {
		SocketChannel client = (SocketChannel) selectionKey.channel();
		int count;
		// 读
		if (selectionKey.isReadable()) {
			buffer.clear();
			try {
				count = client.read(buffer);
				System.out.println("read count: " + count);
				// 暂时不考虑buffer满了的问题
				if (count > 0) {
					String recv = new String(buffer.array(), 0, count, "UTF-8");
					System.out.println("recv-->" + recv);
					// 将接受的数据添加要发送的地方
					toSend.put(client, recv);
					// 等待写入
					client.register(selector, SelectionKey.OP_WRITE);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 写
		if (selectionKey.isWritable()) {
			buffer.clear();
			String send = (String) toSend.get(client);
			buffer.put(send.getBytes());
			buffer.flip();
			try {
				count = client.write(buffer);
				System.out.println("write count:" + count);
				if (count > 0) {
					// 写一个之后就关闭链接
					client.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		NIOEchoServer server = new NIOEchoServer(8787);
		server.listen();
	}

}
