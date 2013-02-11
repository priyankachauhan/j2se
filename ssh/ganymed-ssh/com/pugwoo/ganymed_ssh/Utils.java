package com.pugwoo.ganymed_ssh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.List;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * 这是网上一个封装得很好的接口，又当例子又可以使用 2012年11月17日 上午10:37:35 自己又修改了一下
 */
public class Utils {

	/**
	 * 获得一个链接实例
	 */
	public static Connection getConnection(Host host) throws IOException {
		Connection conn = new Connection(host.getHost(), host.getPort());
		conn.connect();

		if (host.getUsername() != null) {
			boolean isAuthenticated = conn.authenticateWithPassword(host
					.getUsername(), host.getPassword());
			if (!isAuthenticated)
				throw new IOException("Authentication failed.");
		}

		return conn;
	}

	/**
	 * 执行【一行】命令（可以多个命令用;分开）,无法交互
	 * 返回null时为有错误发生，其它为正常
	 */
	public static String run(Connection conn, String cmd) throws IOException {
		Session sess = conn.openSession();
		// important
		sess.execCommand(cmd);

		// 使用StreamGobbler可以加快70ms，原因是使用多线程
		InputStream stdout = new StreamGobbler(sess.getStdout());
		InputStream stderr = new StreamGobbler(sess.getStderr());

		BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(
				stdout));
		BufferedReader stderrReader = new BufferedReader(new InputStreamReader(
				stderr));

		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = stderrReader.readLine()) != null) {
			sb.append(line).append("\n");
		}
		if (sb.length() > 0) {
			System.err.println(sb);
			sess.close();
			return null;
		}

		while ((line = stdoutReader.readLine()) != null) {
			sb.append(line).append("\n");
		}

		sess.close();
		// 去掉最后一个回车符
		int len = sb.length();
		if (len == 0) {
			return "";
		} else {
			return sb.substring(0, len - 1).toString();
		}
	}

	/**
	 * 在远程机器上执行本机的一个bash文件，bash文件中的context必须兼容远程机器
	 * 无标准输入，例如在中途输入密码等
	 * 返回null时为有错误发生，其它为正常
	 */
	public static String run(Connection conn, File file) throws IOException {
		FileReader reader = new FileReader(file);
		BufferedReader br = new BufferedReader(reader);
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			sb.append(line).append("\n");
		}
		return run(conn, sb.toString());
	}

	/**
	 * 将java文件在机器conn上运行
	 * files可以是jar包、java文件或class文件
	 * 结果返回为String
	 * 
	 * 缺点：目标主机必须安装java
	 */
	public static String runJava(Connection conn, List<File> files, String cmd) {
		File file = new File("C:/a.txt");
		file.getPath();
		// todo
		return null;
	}

	/**
	 * 获得一个可以交互的输入输出流
	 */
	public static Streams getStreams(Connection conn) throws IOException {
		Session sess = conn.openSession();
		int x_width = 120;
		int y_width = 30;

		sess.requestPTY("dumb", x_width, y_width, 0, 0, null);
		sess.startShell();

		Streams streams = new Streams();
		streams.setSession(sess);

		// 使用StreamGobbler可以加快70ms，原因是使用多线程
		InputStream stdout = new StreamGobbler(sess.getStdout());
		InputStream stderr = new StreamGobbler(sess.getStderr());

		streams.setStdout(stdout);
		streams.setStderr(stderr);
		streams.setStdin(sess.getStdin());

		streams.setStdoutReader(new BufferedReader(
				new InputStreamReader(stdout)));
		streams.setStderrReader(new BufferedReader(
				new InputStreamReader(stderr)));
		streams.setStdinPrint(new PrintStream(sess.getStdin()));

		return streams;
	}

	/**
	 * 从远程机器上下载文件到本机
	 */
	public static void scpFrom(Connection conn, String remoteFile,
			String localDir) throws IOException {
		SCPClient client = new SCPClient(conn);
		client.get(remoteFile, localDir);
	}

	/**
	 * 从本机上传文件到远程机器
	 */
	public static void scpTo(Connection conn, String localFile, String remoteDir)
			throws IOException {
		SCPClient client = new SCPClient(conn);
		client.put(localFile, remoteDir);
	}

	/**
	 * 计算conn对应机器上文件file的md5值,返回null表示出错
	 * 目前只支持一个文件，如果有多个，只取第一个的值
	 */
	public static String md5(Connection conn, String file) throws IOException {
		StringBuilder sb = new StringBuilder("md5sum ");
		sb.append(file).append("|awk 'NR=1{print $1}'");
		return run(conn, sb.toString());
	}

	/**
	 * 执行本机命令
	 */
	// public static int runLocal(String cmd) throws IOException {
	// Runtime rt = Runtime.getRuntime();
	// Process p = rt.exec(cmd);
	// InputStream stdout = new StreamGobbler(p.getInputStream());
	// BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
	// while (true) {
	// String line = br.readLine();
	// if (line == null)
	// break;
	// }
	// return p.exitValue();
	// }
}
