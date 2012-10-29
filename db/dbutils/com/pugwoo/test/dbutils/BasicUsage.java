package com.pugwoo.test.dbutils;

import java.sql.SQLException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

/**
 * 2011年11月13日
 * 
 * @author pugwoo
 */
public class BasicUsage extends TestCase{

	/**
	 * 两种获得QueryRunner方法
	 * 
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public static void useQueryRunner() throws SQLException {
		// 第一种，使用Connection
		QueryRunner qr1 = new QueryRunner();
		ArrayList result1 = (ArrayList) qr1.query(DB.getConnection(),
				"select * from person", new ArrayListHandler());
		System.out.println(result1.size());

		// 第二种，使用DataSource
		QueryRunner qr2 = new QueryRunner(DB.getDataSource());
		ArrayList result2 = (ArrayList) qr2.query("select * from person",
				new ArrayListHandler());
		System.out.println(result2.size());
	}

	/**
	 * 测试插入
	 */
	public static void testInsert() {
		String sql = "INSERT INTO person(name, age, address) values (?, ?, ?)";
		QueryRunner qr = new QueryRunner();
		try {
			qr.update(DB.getConnection(), sql, "karen", 23, "sz");
			// 如果需要获得插入后的自增ID，则SELECT LAST_INSERT_ID()
		} catch (SQLException e) {
			// 如果update出错，就先关闭数据库链接
			DB.closeConnection();
			e.printStackTrace();
		}
	}

	/**
	 * 更新、删除和插入一样的，都用qr.update()函数
	 */

	/**
	 * 查询参见类：Query
	 */

	/**
	 * 批量处理，掩饰批量插入，更新和删除同理
	 * 
	 * @throws SQLException
	 */
	public static void testBatchInsert() {
		String sql = "INSERT INTO person(name, age, address) values (?, ?, ?)";
		QueryRunner qr = new QueryRunner();
		Object[][] data = new Object[][] { { "aaa", 10, "addr1" },
				{ "bbb", 11, "addr2" }, { "ccc", 12, "addr3" } };

		try {
			qr.batch(DB.getConnection(), sql, data);
		} catch (SQLException e) {
			// 如果update出错，就先关闭数据库链接
			DB.closeConnection();
			e.printStackTrace();
		}
	}

}
