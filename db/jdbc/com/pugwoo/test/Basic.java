package com.pugwoo.test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 2011年1月23日 下午09:15:50
 */
public class Basic {

	public static void main(String args[]) {
		// driver class
		String driver = "com.mysql.jdbc.Driver";

		// 数据库地址，比如先创建数据库jdbc和对应的表
		String url = "jdbc:mysql://localhost:3306/jdbc";

		String user = "root";
		String password = "root";

		try {
			// 注册Driver
			Class.forName(driver);

			// 获得连接
			Connection conn = DriverManager.getConnection(url, user, password);

			// 获得Statement
			Statement stat = conn.createStatement();

			// 查询
			ResultSet rs = stat
					.executeQuery("select id,name,age,address from person");

			// 输出
			while (rs.next()) {
				System.out.println("id:" + rs.getInt(1) + ",name:"
						+ rs.getString(2) + ",age:" + rs.getInt(3)
						+ ",address:" + rs.getString(4));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
