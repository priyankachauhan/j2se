package com.pugwoo.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Transation2 {

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
