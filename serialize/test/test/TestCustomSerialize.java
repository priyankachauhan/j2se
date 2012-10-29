package test;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import custom_serialize.Student;


public class TestCustomSerialize {

	/**
	 *  使用java自带的ObjectOutputStream ObjectInputStream
	 *  进行序列化和反序列化
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// ObjectOutputStream序列化
		FileOutputStream f = new FileOutputStream("tmp_TestCustomSerialize");
		ObjectOutputStream s = new ObjectOutputStream(f);
//		s.writeObject("Today");
		// 自己的序列化类
		Student student = new Student();
		student.setName("nick");
		student.setScore(99.9f);
		s.writeObject(student);
		
		s.flush();
		
		// ObjectInputStream反序列化
		FileInputStream in = new FileInputStream("tmp_TestCustomSerialize");
		ObjectInputStream ins = new ObjectInputStream(in);
//		String today = (String) ins.readObject();
		Student stu = (Student) ins.readObject();
		
//		System.out.println(today);
		System.out.println(stu);
	}

}
