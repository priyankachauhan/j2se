package com.pugwoo;

import java.sql.Date;
import java.util.List;

/**
 * 2013年2月27日 21:19:42
 */
public class FileInfo {

	private String filename; // 文件名称
	private String type; // 文件类型，d为目录，和ls -l显示一致
	private String permission; // 权限，9个字符
	private String owner;
	private String group;
	private String size; // 文件大小
	private Date modifiedTime; // 修改时间

	private String md5; // 文件md5值
	private List<FileInfo> files; // 如果是目录，则存放目录文件

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public List<FileInfo> getFiles() {
		return files;
	}

	public void setFiles(List<FileInfo> files) {
		this.files = files;
	}

}
