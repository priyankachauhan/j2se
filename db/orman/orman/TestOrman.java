package orman;

import java.util.List;

import junit.framework.TestCase;

import org.orman.dbms.Database;
import org.orman.dbms.mysql.MySQL;
import org.orman.dbms.mysql.MySQLSettingsImpl;
import org.orman.mapper.MappingSession;
import org.orman.mapper.Model;
import org.orman.mapper.ModelQuery;
import org.orman.sql.C;
import org.orman.sql.Query;

import orman.model.Student;

/**
 * 2013年3月31日
 */
public class TestOrman extends TestCase {

	static {
		// 【不会自动创建数据库】，必须手动创建orman
		Database db = new MySQL(new MySQLSettingsImpl("root", "root", "orman",
				"127.0.0.1", 3306));
		MappingSession.registerDatabase(db);
		// 虽然文档说这个Student只要注解了Entity就会自动找到，但实际上，常常找不到，要在这里注册
		//MappingSession.registerEntity(Student.class);
		// 或者注册整个package
		MappingSession.registerPackage("orman.model");

		// 注册数据库和注册类必须在start之前完成
		MappingSession.start();
	}

	public void testInsert() {
		// 如果表不存在，会自动创建表
		Student s = new Student();
		s.name = "John Doe";
		s.insert();
	}

	public void testQuery() {
		List<Student> list = Model.fetchAll(Student.class);
		// 等价于
		// List<Student> students = Model.fetchQuery(ModelQuery.select().from(Payment.class).getQuery(), Student.class);
		System.out.println("count:" + list.size());
		for (Student s : list) {
			System.out.println(s);
		}
	}

	public void testQuery2() {
		List<Student> list = Model.fetchQuery(ModelQuery.select().from(
				Student.class).where(C.like("name", "%John%")).getQuery(),
				Student.class);
		System.out.println("count:" + list.size());
		for (Student s : list) {
			System.out.println(s);
		}
	}
	
	// native sql
	public void testQuery3() {
		Query q = new Query("select * from student");
		List<Student> list = Model.fetchQuery(q, Student.class);
		System.out.println("count:" + list.size());
		for (Student s : list) {
			System.out.println(s);
		}
	}

	public void testUpdate() {
		
	}

	public static void main(String[] args) {

	}
}
