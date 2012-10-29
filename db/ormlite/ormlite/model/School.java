package ormlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author Administrator
 * @date 2011-12-13
 * 
 */

// 如果类名和表名相同，则不用加上tableName的属性
@DatabaseTable
public class School {

	// 至少有一个是ID
	@DatabaseField(id = true)
	private Long id;

	@DatabaseField
	private String name;

	@DatabaseField
	private String location;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
