package custom_serialize;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * 手动实现序列化
 * 
 * @author Administrator
 * 
 */
public class Student implements Externalizable {

	// 姓名
	private String name;

	// 成绩
	private Float score;

	// 反序列化
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
//		name = (String) in.readObject();
//		score = (Float) in.readObject();
		name = (String) in.readObject();
		score = in.readFloat();
	}

	// 序列化
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
//		out.writeObject(name);
//		out.writeObject(score);
		// 对于string，使用writeObject就可以了，不会加上String的类名
		out.writeObject(name);
		out.writeFloat(score);
	}
	
	@Override
	public String toString() {
		return "name:" + name + ",score:" + score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

}
