package com.pugwoo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.oro.text.regex.MalformedPatternException;

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
 */
public class ExpectClient {

	private static final int COMMAND_EXECUTION_FAIL_OPCODE = -2;
	private static String ENTER_CHARACTER = "\r";
	// 只能通过这些正则表达式判断现在是输入命令的状态，但这样无法100%正确,不支持行首行尾^$
	// 根据各自系统的提示添加匹配
	private static String[] linuxPromptRegEx = new String[] { ">",
			"\\[.*@.*\\][#,$]" };

	private Session session = null;
	private ChannelShell channel = null;
	private Expect4j expect = null;

	boolean hasExecuted = false; // 用于判断是否有命令发送去执行，只有执行的返回才会加入outbuf
	boolean readyToInput = false; // 标记shell命令输入就绪，可以输入
	private StringBuilder outbuf = new StringBuilder();
	private Closure closure = new Closure() {
		public void run(ExpectState expectState) throws Exception {
			if (hasExecuted) {
				outbuf.append(expectState.getBuffer());
			}
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
		channel.connect();
	}

	public ExpectClient(String host, int port, String userName, String password)
			throws Exception {
		this.host = host;
		this.userName = userName;
		this.password = password;
		this.port = port;
		init();
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
	public void delExpectInput(String patternRegEx, String input) {
		matchInput.remove(patternRegEx);
		delBindMatch(patternRegEx);
	}

	/**
	 * 单线程调用，返回null表示执行失败
	 */
	public String execute(String cmd) throws Exception {
		outbuf.delete(0, outbuf.length()); // 清空输出
		hasExecuted = false;
		boolean executeEnd = false;

		while (!executeEnd) {
			if (!readyToInput) {
				int ret = expect.expect(matchPattern);
				if (ret == COMMAND_EXECUTION_FAIL_OPCODE) {
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
					// 由于expect无法正则匹配行首行尾^$，这里就再加强判断
					boolean atLeastMatchOne = false;
					for (String regEx : linuxPromptRegEx) {
						if (Pattern.compile("^" + regEx + "$").matcher(pattern)
								.find()) {
							atLeastMatchOne = true;
							break;
						}
					}
					if (!atLeastMatchOne) { // 至少要匹配一个输入提示
						continue;
					}

					readyToInput = true;
					// 把outbuf最后的输入提示去掉
					if (hasExecuted) {
						int len = outbuf.length();
						outbuf.delete(len - pattern.length(), len);
					}
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

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();

		ExpectClient ssh = new ExpectClient("192.168.56.102", 22, "root",
				"123456");

		// 正常命令测试
		System.out.print(ssh.execute("date"));
		System.out.print(ssh.execute("echo hello world"));

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
}
