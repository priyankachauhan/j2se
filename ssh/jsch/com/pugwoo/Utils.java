package com.pugwoo;

import java.util.List;

/**
 * 2013年2月19日 21:38:30
 * @author pugwoo
 */
public class Utils {

	/**
	 * 获得文件的md5
	 */
	public static String getMD5(ExpectClient client, String filename)
			throws Exception {
		String fn = filename.replace("\"", "\\\"");
		String md5 = client.executeOnce("md5sum \"" + fn + "\"");
		if (md5 == null) {
			return null;
		} else {
			String[] strs = md5.split(" ");
			return strs.length == 0 ? null : strs[0];
		}
	}

	/**
	 * 获得远程文件/文件夹的文件信息
	 */
	public static List<FileInfo> getFileInfo(ExpectClient client,
			String filename) {

	}

	/**
	 * 将文件从A机器传输到B机器
	 */
	public static String scp(ExpectClient client, Host hostSrc, String fileSrc,
			Host hostDest, String fileDest) throws Exception {
		String result;

		// 登录机器hostSrc
		StringBuilder ssh = new StringBuilder("ssh");
		ssh.append(" -p ").append(hostSrc.getPort());
		ssh.append(" ").append(hostSrc.getUsername());
		ssh.append("@").append(hostSrc.getHost());
		client.addExpectInput("(yes/no)", "yes");
		client.addExpectInput("password", hostSrc.getPassword());
		result = client.execute(ssh.toString());
		if (result == null)
			return null;

		// 执行拷贝命令
		StringBuilder scp = new StringBuilder("scp -r -P ");
		scp.append(hostDest.getPort());
		scp.append(" ").append(fileSrc);
		scp.append(" ").append(hostDest.getUsername());
		scp.append("@").append(hostDest.getHost());
		scp.append(":").append(fileDest);

		client.delExpectInput("password");
		client.addExpectInput("password", hostDest.getPassword());
		result = client.execute(scp.toString());
		if (result == null)
			return null;

		return result;
	}
}
