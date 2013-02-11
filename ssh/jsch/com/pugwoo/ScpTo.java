package com.pugwoo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * 把本地文件复制到远程机器上
 * 2013年2月9日 23:43:41
 */
public class ScpTo {

	public static void main(String[] args) throws Exception {

		String host = "192.168.56.102";
		String user = "root";
		String password = "123456";

		String lfile = "I:/soft/virtualbox_bak/winxp.7z";
		String rfile = "/share/";

		// 连接获得session
		JSch jsch = new JSch();
		Session session = jsch.getSession(user, host, 22);
		session.setPassword(password);
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect();

		long start = System.currentTimeMillis();
		int ret = scpTo(session, lfile, rfile);
		long stop = System.currentTimeMillis();
		System.out.println((stop - start) + "ms");
		if (ret == 0) {
			System.out.println("传输成功");
		}

		session.disconnect();

	}

	private static int scpTo(Session session, String lfile, String rfile)
			throws JSchException, IOException {
		return scpTo(session, lfile, rfile, true);
	}

	/**
	 * session不会被关掉
	 */
	private static int scpTo(Session session, String lfile, String rfile,
			boolean ptimestamp) throws JSchException, IOException {
		Channel channel = session.openChannel("exec");

		// exec 'scp -t rfile' remotely
		String command = "scp " + (ptimestamp ? "-p" : "") + " -t " + rfile;
		((ChannelExec) channel).setCommand(command);

		// get I/O streams for remote scp
		OutputStream out = channel.getOutputStream();
		InputStream in = channel.getInputStream();

		channel.connect();

		// 每次传输数据都会有ack
		int ack = checkAck(in);
		if (ack != 0) {
			return ack;
		}

		File _lfile = new File(lfile);

		if (ptimestamp) {
			command = "T " + (_lfile.lastModified() / 1000) + " 0";
			// The access time should be sent here,
			// but it is not accessible with JavaAPI ;-<
			command += (" " + (_lfile.lastModified() / 1000) + " 0\n");
			out.write(command.getBytes());
			out.flush();
			ack = checkAck(in);
			if (ack != 0) {
				return ack;
			}
		}

		// send "C0644 filesize filename", where filename should not include '/'
		long filesize = _lfile.length();
		command = "C0644 " + filesize + " ";
		if (lfile.lastIndexOf('/') > 0) {
			command += lfile.substring(lfile.lastIndexOf('/') + 1);
		} else {
			command += lfile;
		}
		command += "\n";
		out.write(command.getBytes());
		out.flush();
		ack = checkAck(in);
		if (ack != 0) {
			return ack;
		}

		FileInputStream fis = null;
		try {
			// send a content of lfile
			fis = new FileInputStream(lfile);
			byte[] buf = new byte[4096];
			while (true) {
				int len = fis.read(buf, 0, buf.length);
				if (len <= 0)
					break;
				out.write(buf, 0, len); //out.flush();
			}
			fis.close();
			fis = null;
			// send '\0'
			buf[0] = 0;
			out.write(buf, 0, 1);
			out.flush();
			ack = checkAck(in);
			if (ack != 0) {
				return ack;
			}
			out.close();

			channel.disconnect();

			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (fis != null)
					fis.close();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
			return -1;
		}
	}

	private static int checkAck(InputStream in) throws IOException {
		int b = in.read();
		// b may be 0 for success,
		//          1 for error,
		//          2 for fatal error,
		//          -1
		if (b == 0)
			return b;
		if (b == -1)
			return b;

		if (b == 1 || b == 2) {
			StringBuffer sb = new StringBuffer();
			int c;
			do {
				c = in.read();
				sb.append((char) c);
			} while (c != '\n');
			if (b == 1) { // error
				System.err.println(sb.toString());
			}
			if (b == 2) { // fatal error
				System.err.println(sb.toString());
			}
		}
		return b;
	}
}
