package reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import junit.framework.TestCase;

/**
 * 2011年2月4日 下午09:40:33
 * 
 * 2011-12-11 21:10
 * 新增：http://www.cnblogs.com/stephen-liu74/archive/2011/09/04/2166192.html
 * 
 */
@SuppressWarnings("unused")
public class Basic extends TestCase {

	public static void main(String args[]) throws ClassNotFoundException {

		// Step 1 获得Class对象，两种方式
		// Class clazz = Class.forName("model.User"); //方式一
		Class<?> clazz = model.User.class; // 方式二

		// Step 2 获得某个Class对象的所有方法，Method对象可以用来调用方法
		Method[] methods = clazz.getMethods();
		// for(int i=0; i< methods.length; i++)
		// System.out.println(methods[i].getName()); //此外还可以获得该方法的参数

		// 常用：获得构造函数，构造对象可以用来创建新实例
		Constructor<?> ctorlist[] = clazz.getDeclaredConstructors();

		// 常用：获得属性
		Field fieldlist[] = clazz.getDeclaredFields();

		// 常用：判断某个对象是不是属于某个Class
		System.out.println(clazz.isInstance(new model.User()));

		System.out.println(clazz.getName());
	}

	/**
	 * 通过String形式的类名获得Class和实例
	 * 
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public void testString2Class() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		// 通过String获得Class对象
		Class<?> clazz = Class.forName("java.lang.String");
		System.out.println(clazz.getName());

		// 通过Class对象获得实例（无法给定参数，所以要求有默认构造方法）
		String str = (String) clazz.newInstance();
		System.out.println(str);
	}

	/**
	 * 获得类的修饰符
	 */
	public void testGetModifier() {
		Class<? extends String> clazz = "".getClass();
		// 获得修饰符
		int m = clazz.getModifiers();
		System.out.println(Modifier.toString(m));
		// 可以使用Modifier.is***()等方法
	}

	/**
	 * 获得类的域Field（成员数据）
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void testGetField() throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = "".getClass();
		// 获得域Field
		//Field fields[] = clazz.getDeclaredFields();
		Field fields[] = clazz.getFields();
		for (Field field : fields) {
			System.out.println(Modifier.toString(field.getModifiers()) + " "
					+ field.getType().getName() + " " + field.getName());
			System.out.println(field.get(""));
		}
	}
}
