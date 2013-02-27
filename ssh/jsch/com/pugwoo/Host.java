package com.pugwoo;

/**
 * 2013年2月19日 21:32:33
 */
public class Host {

	private String host = "localhost";
	private int port = 22;
	private String username;
	private String password;

	public Host() {
	}

	public Host(String host, int port, String username, String password) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public Host(String host, String username, String password) {
		this(host, 22, username, password);
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
