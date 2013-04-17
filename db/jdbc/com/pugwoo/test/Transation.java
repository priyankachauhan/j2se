package com.pugwoo.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Transation {

	public static void main(String[] args) {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/jdbc";
		String user = "root";
		String password = "root";
		
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			
			// 进行事务操作
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select id,name,age,address from person where id=1" +
					" for update");
			
			if(rs.next()) {
				System.out.println("id:" + rs.getInt(1) + ",name:"
						+ rs.getString(2) + ",age:" + rs.getInt(3)
						+ ",address:" + rs.getString(4));
			}
			rs.close();
			
			/**
			 * 经过测试，在sleep期间
			 * 如果单单是select: 这行记录是可以被修改的
			 * 如果加上for update: 其它update该行或者select for update会被锁住
			 */
			System.out.println("sleep 100 sec");
			Thread.sleep(100 * 1000);
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
