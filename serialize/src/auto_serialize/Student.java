package auto_serialize;
import java.io.Serializable;

/**
 * 自定义可以【自动】序列化的类
 * 只要实现Serializable接口就可以了
 * 
 * @author Administrator
 */
public class Student implements Serializable {

	/**
	 * 序列化和反序列化这个值必须一致
	 *【实际上】序列化和反序列化可以是不同的类
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 【重要】序列化奖不会自动序列化声明为transient或static的成员
	 */

	// 姓名
	private String name;

	// 成绩
	private Float score;

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
