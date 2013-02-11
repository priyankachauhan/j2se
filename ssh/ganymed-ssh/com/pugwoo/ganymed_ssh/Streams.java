package com.pugwoo.ganymed_ssh;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import ch.ethz.ssh2.Session;

/**
 * 2013年2月9日 09:11:25
 * start一个shell后，返回的输入输出流
 */
public class Streams {
	
	Session session;

	// stdout类型是输入流，虽然名称是out，它是shell的结果输出
	private InputStream stdout;
	private InputStream stderr;
	private BufferedReader stdoutReader;
	private BufferedReader stderrReader;

	private OutputStream stdin;
	private PrintStream stdinPrint;

	public InputStream getStdout() {
		return stdout;
	}

	public void setStdout(InputStream stdout) {
		this.stdout = stdout;
	}

	public InputStream getStderr() {
		return stderr;
	}

	public void setStderr(InputStream stderr) {
		this.stderr = stderr;
	}

	public BufferedReader getStdoutReader() {
		return stdoutReader;
	}

	public void setStdoutReader(BufferedReader stdoutReader) {
		this.stdoutReader = stdoutReader;
	}

	public BufferedReader getStderrReader() {
		return stderrReader;
	}

	public void setStderrReader(BufferedReader stderrReader) {
		this.stderrReader = stderrReader;
	}

	public OutputStream getStdin() {
		return stdin;
	}

	public void setStdin(OutputStream stdin) {
		this.stdin = stdin;
	}

	public PrintStream getStdinPrint() {
		return stdinPrint;
	}

	public void setStdinPrint(PrintStream stdinPrint) {
		this.stdinPrint = stdinPrint;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
