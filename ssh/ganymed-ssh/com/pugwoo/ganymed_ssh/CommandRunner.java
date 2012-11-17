package com.pugwoo.ganymed_ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * 这是网上一个封装得很好的接口，又当例子又可以使用
 */
public class CommandRunner {
	private static final Logger logger = Logger.getLogger(CommandRunner.class);

	/**
	 * 从远程机器上下载文件到本机
	 */
	public static void scpGet(String host, String username, String password,
			String remoteFile, String localDir) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("spc [" + remoteFile + "] from " + host + " to "
					+ localDir);
		}
		Connection conn = getOpenedConnection(host, username, password);
		SCPClient client = new SCPClient(conn);
		client.get(remoteFile, localDir);
		conn.close();
	}

	/**
	 * 从本机上传文件到远程机器
	 */
	public static void scpPut(String host, String username, String password,
			String localFile, String remoteDir) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("spc [" + localFile + "] to " + host + remoteDir);
		}
		Connection conn = getOpenedConnection(host, username, password);
		SCPClient client = new SCPClient(conn);
		client.put(localFile, remoteDir);
		conn.close();
	}

	/**
	 * 执行一个命令
	 */
	public static int runSSH(String host, String username, String password,
			String cmd) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("running SSH cmd [" + cmd + "]");
		}
		Connection conn = getOpenedConnection(host, username, password);
		Session sess = conn.openSession();
		sess.execCommand(cmd);
		InputStream stdout = new StreamGobbler(sess.getStdout());
		BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
		while (true) {
			String line = br.readLine();
			if (line == null)
				break;
			if (logger.isDebugEnabled()) {
				logger.debug(line);
			}
		}
		sess.close();
		conn.close();
		return sess.getExitStatus().intValue();
	}

	private static Connection getOpenedConnection(String host, String username,
			String password) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("connecting to " + host + " with user " + username
					+ " and pwd " + password);
		}
		Connection conn = new Connection(host);
		conn.connect(); // make sure the connection is opened
		boolean isAuthenticated = conn.authenticateWithPassword(username,
				password);
		if (isAuthenticated == false)
			throw new IOException("Authentication failed.");
		return conn;
	}

	/**
	 * 执行本机命令
	 */
	public static int runLocal(String cmd) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("running local cmd [" + cmd + "]");
		}
		Runtime rt = Runtime.getRuntime();
		Process p = rt.exec(cmd);
		InputStream stdout = new StreamGobbler(p.getInputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
		while (true) {
			String line = br.readLine();
			if (line == null)
				break;
			if (logger.isDebugEnabled()) {
				logger.debug(line);
			}
		}
		return p.exitValue();
	}
}
