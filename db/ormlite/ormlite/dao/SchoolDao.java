package ormlite.dao;

import java.sql.SQLException;

import junit.framework.TestCase;
import ormlite.model.School;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
/**
 * 2011年12月13日 下午08:38:47
 */
public class SchoolDao extends TestCase {

	private static Dao<School, Long> schoolDao;
	
	private static ConnectionSource connectionSource;

	static {
		String DBUrl = "jdbc:mysql://127.0.0.1:3306/test?user=root&password=root";
		
		try {
			connectionSource = new JdbcConnectionSource(DBUrl);
			schoolDao = DaoManager.createDao(connectionSource, School.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 自动创建表
	 */
	public void testCreateTable() throws SQLException {
		TableUtils.dropTable(connectionSource, School.class, true);
		TableUtils.createTable(connectionSource, School.class);
	}

	/**
	 * 插入
	 */
	public void testInsert() throws SQLException {
		School school = new School();
		school.setId(new Double(Math.random() * 10000L).longValue());
		school.setName("sysu");

		schoolDao.create(school);
	}
	
	public void testUpdate() throws SQLException {
		School school = new School();
		school.setId(333L);
		school.setName("sysu");
		school.setLocation("canton");
		
		schoolDao.create(school);
		
		School school2 = new School();
		school2.setId(333L);
		school2.setName("sysu2");
		
		school.setName("sysu2");
		// 只能一次更新所有数据
		schoolDao.update(school);
		
	}

	public static void main(String[] args) throws SQLException {

	}
}
