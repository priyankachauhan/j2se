package com.pugwoo.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.Set;

// 这个程序还有问题，当读取到结尾时，应该关闭channel
public class NIOServerSimple {

	public static void main(String[] args) throws IOException {

		// 不要用encoder和decoder，这两个不是线程安全，用String来编码和解码，推荐用utf-8
		Charset charset = Charset.forName("ISO-8859-1");
		CharsetEncoder encoder = charset.newEncoder();
		CharsetDecoder decoder = charset.newDecoder();

		ByteBuffer buffer = ByteBuffer.allocate(512);

		Selector selector = Selector.open();

		ServerSocketChannel server = ServerSocketChannel.open();
		server.socket().bind(new java.net.InetSocketAddress(8787));
		server.configureBlocking(false);
		SelectionKey serverkey = server.register(selector,
				SelectionKey.OP_ACCEPT);

		for (;;) {
			selector.select();
			Set keys = selector.selectedKeys();

			for (Iterator i = keys.iterator(); i.hasNext();) {
				SelectionKey key = (SelectionKey) i.next();
				i.remove();

				// 这就是说，这个key代表着一个channel的某个操作类型？？
				if (key == serverkey) {
					if (key.isAcceptable()) {
						SocketChannel client = server.accept();
						client.configureBlocking(false);
						SelectionKey clientkey = client.register(selector,
								SelectionKey.OP_READ);
						clientkey.attach(new Integer(0));
					}
				} else {
					SocketChannel client = (SocketChannel) key.channel();
					if (!key.isReadable())
						continue;
					int bytesread = client.read(buffer);
					if (bytesread == -1) {
						key.cancel();
						client.close();
						continue;
					}
					buffer.flip();
					String request = decoder.decode(buffer).toString();
					buffer.clear();
					if (request.trim().equals("quit")) {
						client.write(encoder.encode(CharBuffer.wrap("Bye.")));
						key.cancel();
						client.close();
					} else {
						int num = ((Integer) key.attachment()).intValue();
						String response = num + ": " + request.toUpperCase();
						// IMPORTANT 这样子做是不好的!因为client可能还不能写数据，会阻塞导致并发性能降低
						// 还可能出问题：返回值可能为0，此时信息就丢失了。
						client.write(encoder.encode(CharBuffer.wrap(response)));
						key.attach(new Integer(num + 1));
					}
				}
			}
		}
	}
}
