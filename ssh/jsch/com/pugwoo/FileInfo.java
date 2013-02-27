package com.pugwoo;

public class FileInfo {

	private String filename; // 文件名称
	private String type; // 文件类型，d为目录，和ls -l显示一致
	private String permission; // 权限
	private String owner;
	private String group;
	private String size; // 文件大小

	private String md5; // 文件md5值
}
