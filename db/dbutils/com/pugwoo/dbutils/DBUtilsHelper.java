package com.pugwoo.dbutils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 * 2013-9-27 下午12:41:37
 */
@SuppressWarnings("unchecked")
public class DBUtilsHelper {

	/**
	 * 返回单个值，适用于count(*)
	 */
	public static <T> T queryValue(QueryRunner qr, String sql, Object... params)
			throws SQLException {
		return (T) qr.query(sql, new ScalarHandler(), params);
	}

	public static Object[] queryBean(QueryRunner qr, String sql,
			Object... params) throws SQLException {
		return qr.query(sql, new ArrayHandler(), params);
	}

	public static <T> T queryBean(QueryRunner qr, Class<T> clazz, String sql,
			Object... params) throws SQLException {
		return (T) qr.query(sql, new BeanHandler(clazz), params);
	}

	/**
	 * @return 返回一行记录的"列名"->"值"的map
	 */
	public static Map<String, Object> queryMap(QueryRunner qr, String sql,
			Object... params) throws SQLException {
		return qr.query(sql, new MapHandler(), params);
	}

	public static List<Object[]> queryList(QueryRunner qr, String sql,
			Object... params) throws SQLException {
		return qr.query(sql, new ArrayListHandler());
	}
	
	public static <T> List<T> queryList(QueryRunner qr, Class<T> clazz,
			String sql, Object... params) throws SQLException {
		return qr.query(sql, new BeanListHandler(clazz), params);
	}

	public static List<Map<String, Object>> queryListMap(QueryRunner qr,
			String sql, Object... params) throws SQLException {
		return qr.query(sql, new MapListHandler(), params);
	}

	public static <T> Map<T, Map<String, Object>> queryKeyMap(QueryRunner qr,
			String sql, String key, Object... params) throws SQLException {
		ResultSetHandler<?> h = new KeyedHandler(key);
		return (Map<T, Map<String, Object>>) qr.query(sql, h, params);
	}
	
	public static int update(QueryRunner qr,
			String sql, Object... params) throws SQLException {
		return qr.update(sql, params);
	}
	
	public static int[] batch(QueryRunner qr,
			String sql, Object[][] params) throws SQLException {
		return qr.batch(sql, params);
	}

	public static void main(String[] args) {

	}

}
