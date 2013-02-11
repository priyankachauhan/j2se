package com.pugwoo.ganymed_ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * ssh登录一台机器并执行一行命令
 * 
 * 2012年11月16日 下午10:45:23
 */
public class Basic {
	public static void main(String[] args) {
		
		String hostname = "192.168.56.102";
		String username = "root";
		String password = "123456";

		try {
			Connection conn = new Connection(hostname);
			conn.connect();
			/*
			 * Authenticate. If you get an IOException saying something like
			 * "Authentication method password not supported by the server at this stage."
			 * then please check the FAQ.
			 */
			boolean isAuthenticated = conn.authenticateWithPassword(username,
					password);

			if (isAuthenticated == false)
				throw new IOException("Authentication failed.");

			/* Create a session */
			Session sess = conn.openSession();

//			sess.execCommand("uname -a && date && uptime && who");
			sess.execCommand("ls");

			/*
			 * This basic example does not handle stderr, which is sometimes
			 * dangerous (please read the FAQ).
			 */
			InputStream stdout = new StreamGobbler(sess.getStdout());
			BufferedReader br = new BufferedReader(
					new InputStreamReader(stdout));

			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				System.out.println(line);
			}

			/* Show exit status, if available (otherwise "null") */
			System.out.println("ExitCode: " + sess.getExitStatus());
			/* Close this session */
			sess.close();
			/* Close the connection */
			conn.close();

		} catch (IOException e) {
			e.printStackTrace(System.err);
			System.exit(2);
		}
	}
}
