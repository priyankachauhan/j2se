package orman.model;

import java.util.Date;

import org.orman.mapper.Model;
import org.orman.mapper.annotation.Column;
import org.orman.mapper.annotation.Entity;
import org.orman.mapper.annotation.NotNull;
import org.orman.mapper.annotation.PrimaryKey;

/**
 * 2013年3月31日 20:26:08
 * 如果是private数据成员，那必须提供getter/setter
 * 必须有无参数构造函数
 * 
 * 自定义表名、列明、自增、notnull、列类型等
 * https://github.com/ahmetalpbalkan/orman/wiki/Entity-and-field-customizations
 */
@Entity
public class Student extends Model<Student> {
	@PrimaryKey(autoIncrement = true)
	public int id;
	@NotNull
//	@Index(name = "name_index", type = IndexType.BTREE)
//  不知道为什么index插入多过一次有问题
	public String name;
	@Column(name="regdate")
	public Date registrationDate;
	public float gpa;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id:").append(id);
		sb.append(",name:").append(name);
		sb.append(",regDate:").append(registrationDate);
		sb.append(",gpa:").append(gpa);
		return sb.toString();
	}
}
