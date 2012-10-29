package reflect;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import model.User;



/**
 * 2011-11-14 21:10:17
 * 供Bean的getter和setter使用的Reflect
 * 这个从dbutils里面学到的
 */
public class BeanReflect {

	public static void main(String[] args) throws IntrospectionException {

		// 第一步，获得beanInfo
		BeanInfo beanInfo = Introspector.getBeanInfo(User.class);

		// 第二步，获得PropertyDescriptor，这些就是由getter和setter的方法的属性了
		PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();

		for (PropertyDescriptor prop : props) {
			System.out.println(prop.getName());
			// 第三步，重要的使用

			// 获得该属性的类型
			System.out.println(prop.getPropertyType());
			// 获得该属性的getter方法
			System.out.println(prop.getReadMethod());
			// 获得该属性的setter方法
			System.out.println(prop.getWriteMethod());			
			
		}

	}

}
