package com.pugwoo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 来自：http://xm-king.iteye.com/blog/766330
 * 写得非常规范
 */
public class NIOServer {

	/* 缓冲区大小 */
	private int BUFFER_SIZE = 32;
	/* 接受数据缓冲区 */
	private ByteBuffer sendbuffer = ByteBuffer.allocate(BUFFER_SIZE);
	/* 发送数据缓冲区 */
	private ByteBuffer receivebuffer = ByteBuffer.allocate(BUFFER_SIZE);
	/* 选择器，相当于一个观察者，观察着若干的channel */
	private Selector selector;

	public NIOServer(int port) throws IOException {
		// 打开服务器套接字通道
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		// 服务器配置为非阻塞
		serverSocketChannel.configureBlocking(false);
		// 检索与此通道关联的服务器套接字
		ServerSocket serverSocket = serverSocketChannel.socket();
		// 进行端口绑定
		serverSocket.bind(new InetSocketAddress(port));

		// 通过open()方法找到Selector，默认在JDK6+Linux2.6+上是用Epoll模型
		selector = Selector.open();
		// 注册到selector，监听链接事件OP_ACCEPT，等待连接
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("Server started successfully at port: " + port);
	}

	// 监听，不停地接受客户端连接
	private void listen() throws IOException {
		while (true) {
			// 阻塞，直到至少有一个事件发生
			selector.select();
			// 返回此选择器的已选择键集。
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				// 一定要删除，否则selector会陷入无尽的循环中，或者其它错误
				iterator.remove();
				handleKey(selectionKey);
			}
		}
	}

	// 处理请求
	private void handleKey(SelectionKey selectionKey) throws IOException {
		// 接受请求
		ServerSocketChannel server = null;
		SocketChannel client = null;
		String receiveText;
		String sendText;
		int count = 0;
		// 测试此键的通道是否已准备好接受新的套接字连接。
		if (selectionKey.isAcceptable()) {
			// 返回为之创建此键的通道。
			server = (ServerSocketChannel) selectionKey.channel();
			// 接受到此通道套接字的连接。
			// 此方法返回的套接字通道（如果有）将处于阻塞模式。
			client = server.accept();
			// 配置为非阻塞
			client.configureBlocking(false);
			// 注册到selector，等待数据
			client.register(selector, SelectionKey.OP_READ);
		} else if (selectionKey.isReadable()) {
			// 返回为之创建此键的通道。
			client = (SocketChannel) selectionKey.channel();
			// 将缓冲区清空以备下次读取
			receivebuffer.clear();
			// 读取服务器发送来的数据到缓冲区中
			count = client.read(receivebuffer);
			if (count > 0) {
				receiveText = new String(receivebuffer.array(), 0, count);
				System.out.println("服务器端接受客户端数据--:" + receiveText);
				client.register(selector, SelectionKey.OP_WRITE);
			}
			// 没有考虑到缓存区满了怎么办？
		} else if (selectionKey.isWritable()) {
			// 将缓冲区清空以备下次写入
			sendbuffer.clear();
			// 返回为之创建此键的通道。
			client = (SocketChannel) selectionKey.channel();
			sendText = "message from server--";
			// 向缓冲区中输入数据
			sendbuffer.put(sendText.getBytes());
			// 将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位
			sendbuffer.flip();
			// 输出到通道
			client.write(sendbuffer);
			System.out.println("服务器端向客户端发送数据--：" + sendText);
			client.register(selector, SelectionKey.OP_READ);
		}
	}

	public static void main(String[] args) throws IOException {
		NIOServer server = new NIOServer(8000);
		server.listen();
	}
}