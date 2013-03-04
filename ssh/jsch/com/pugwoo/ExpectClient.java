package com.pugwoo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.oro.text.regex.MalformedPatternException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import expect4j.Closure;
import expect4j.Expect4j;
import expect4j.ExpectState;
import expect4j.matches.Match;
import expect4j.matches.RegExpMatch;

/**
 * 2013年2月10日 11:33:25
 * author: pugwoo
 * 只支持单线程，设计理念：
 * 1) 依靠ssh命令行提示来判断命令的顺序执行
 * 2) 依靠expect的Map来判断当前的提示要输入什么内容，这个map可以动态改变
 * 
 * 2013年2月28日 07:41:06
 * 考虑加入timeout match，但是无法保证正常性
 */
public class ExpectClient {

	public static String ENTER_CHARACTER = "\r";
	public static String CTRL_C = "\003";
	public static String PROMPT1 = "(^\\[.*@.*\\][#,$] $|(?=.*)\\[.*@.*\\][#,$] $)";
	public static String PROMPT2 = "(^> $|\n> $)";

	// 只能通过这些正则表达式判断现在是输入命令的状态，但这样无法100%正确,不支持行首行尾^$
	// 根据各自系统的提示添加匹配
	private static String[] linuxPromptRegEx = new String[] { PROMPT1, PROMPT2 };

	private Session session = null;
	private ChannelShell channel = null;
	private Expect4j expect = null;

	boolean hasExecuted = false; // 用于判断是否有命令发送去执行，只有执行的返回才会加入outbuf
	boolean readyToInput = false; // 标记shell命令输入就绪，可以输入
	private StringBuilder outbuf = new StringBuilder();
	private Closure closure = new Closure() {
		public void run(ExpectState expectState) throws Exception {
			if (hasExecuted)
				outbuf.append(expectState.getBuffer());
		}
	};
	// 所有要expect的pattern都添加到这单个listPattern中，默认有linux输出提示符号
	private List<Match> matchPattern = new ArrayList<Match>();

	// 用户自定义的提示输入信息，支持正则表达式
	private Map<String, String> matchInput = new HashMap<String, String>();

	private String userName;
	private String password;
	private String host;
	private int port;
	private boolean isInit = false;

	private void init() throws IOException, Exception {
		addBindMatch(linuxPromptRegEx, closure);

		session = new JSch().getSession(userName, host, port);
		if (password != null) {
			session.setPassword(password);
		}
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect(60000);
		channel = (ChannelShell) session.openChannel("shell");
		expect = new Expect4j(channel.getInputStream(), channel
				.getOutputStream());
		// 取消超时机制
		expect.setDefaultTimeout(Expect4j.TIMEOUT_FOREVER);
		channel.connect();
	}

	public ExpectClient(String host, int port, String userName, String password)
			throws Exception {
		this.host = host;
		this.userName = userName;
		this.password = password;
		this.port = port;
	}

	public ExpectClient(Host host) {
		this.host = host.getHost();
		this.port = host.getPort();
		this.userName = host.getUsername();
		this.password = host.getPassword();
	}

	/**
	 * 将promptRegEx对应的处理closure添加到listPattern中并返回
	 */
	private void addBindMatch(String[] promptRegEx, Closure closure) {
		for (String regexEle : promptRegEx) {
			addBindMatch(regexEle, closure);
		}
	}

	private void addBindMatch(String promptRegEx, Closure closure) {
		try {
			matchPattern.add(new RegExpMatch(promptRegEx, closure));
		} catch (MalformedPatternException e) {
			e.printStackTrace();
		}
	}

	private void addBindMatch(String promptRegEx) {
		try {
			matchPattern.add(new RegExpMatch(promptRegEx, closure));
		} catch (MalformedPatternException e) {
			e.printStackTrace();
		}
	}

	private void delBindMatch(String promptRegEx) {
		for (Match match : matchPattern) {
			if (((RegExpMatch) match).getPattern().equals(promptRegEx)) {
				matchPattern.remove(match);
			}
		}
	}

	/**
	 * 用户自定义添加提示pattern和对应的输入
	 */
	public void addExpectInput(String patternRegEx, String input) {
		matchInput.put(patternRegEx, input);
		addBindMatch(patternRegEx);
	}

	/**
	 * 用户自定义删除提示pattern和对应的输入
	 */
	public void delExpectInput(String patternRegEx) {
		matchInput.remove(patternRegEx);
		delBindMatch(patternRegEx);
	}

	/**
	 * 清空用户自定义提示pattern
	 */
	public void clearExpectInput() {
		for (String key : matchInput.keySet()) {
			delExpectInput(key);
		}
	}

	/**
	 * 单线程调用，返回null表示执行失败
	 */
	public String execute(String cmd) throws Exception {
		if (!isInit) {
			init();
			isInit = true;
		}
		cmd = cmd == null ? "" : cmd;

		outbuf.delete(0, outbuf.length()); // 清空输出
		hasExecuted = false;
		boolean executeEnd = false;

		while (!executeEnd) {
			if (!readyToInput) {
				int ret = expect.expect(matchPattern);
				if (ret < 0) {
					System.err.println("ret:" + ret);
					System.err.println(outbuf);
					return null;
				}

				// 通过判断pattern来确定输入，如果在用户自定义的输入中
				String pattern = expect.getLastState().getMatch();

				boolean isPrompt = true;
				for (String key : matchInput.keySet()) {
					if (Pattern.compile(key).matcher(pattern).find()) {
						expect.send(matchInput.get(key));
						expect.send(ENTER_CHARACTER);
						isPrompt = false;
						break;
					}
				}

				if (isPrompt) {
					readyToInput = true;
					// 把outbuf最后的输入提示去掉
					if (hasExecuted)
						outbuf.delete(outbuf.length() - pattern.length(),
								outbuf.length());
				}
			} else {
				if (hasExecuted) {
					executeEnd = true;
				} else {
					expect.send(cmd);
					expect.send(ENTER_CHARACTER);
					readyToInput = false; // 一次readyToInput发送一次命令
					hasExecuted = true;
				}
			}
		}
		return outbuf.toString();
	}

	public void closeConnection() {
		if (expect != null) {
			expect.close();
		}
		if (channel != null) {
			channel.disconnect();
		}
		if (session != null) {
			session.disconnect();
		}
	}

	/**
	 * 执行一行代码，然后关闭连接
	 */
	public String executeOnce(String cmd) throws Exception {
		JSch jsch = new JSch();
		Session session = jsch.getSession(userName, host, 22);
		session.setPassword(password);
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect();

		Channel channel = session.openChannel("exec");
		((ChannelExec) channel).setCommand(cmd);
		((ChannelExec) channel).setErrStream(System.err);
		InputStream in = channel.getInputStream();
		channel.connect();

		StringBuilder sb = new StringBuilder();
		byte[] buf = new byte[1024];
		int exitStatus = 0;
		while (true) {
			while (in.available() > 0) {
				int len = in.read(buf, 0, buf.length);
				if (len < 0)
					break;
				sb.append(new String(buf, 0, len));
			}
			if (channel.isClosed()) {
				exitStatus = channel.getExitStatus();
				break;
			}
			// 异步io轮询
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
		}
		channel.disconnect();
		session.disconnect();

		if (exitStatus != 0) {
			return null;
		} else {
			return sb.toString();
		}
	}

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();

		ExpectClient ssh = new ExpectClient("192.168.56.103", 22, "root",
				"123456");

		// 正常命令测试
		System.out.print(ssh.execute("date"));
		System.out.print(ssh.execute("echo hello world"));
		System.out.print(ssh.execute("echo -n hello world"));

		// 连接测试
		ssh.addExpectInput("(yes/no)", "yes");
		ssh.addExpectInput(".*password", "123456");
		System.out.print(ssh.execute("ssh root@localhost"));

		// 睡眠等待测试
		System.out.print(ssh.execute("sleep 2"));

		// 多行命令测试
		System.out.print(ssh.execute("for((i=0;i<3;i++))"));
		System.out.print(ssh.execute("do"));
		System.out.print(ssh.execute("  echo $i"));
		System.out.print(ssh.execute("done"));

		// 错误信息测试
		System.out.print(ssh.execute("zz"));

		// 关闭连接
		ssh.closeConnection();

		long stop = System.currentTimeMillis();
		System.out.println((stop - start) + "ms");
	}

	public Expect4j getExpect() {
		return expect;
	}

	public void setExpect(Expect4j expect) {
		this.expect = expect;
	}
}
