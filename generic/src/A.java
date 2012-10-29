
public class A<T> {

	public String getString(T t){
		return t.toString();
	}
	
	public static void main(String args[]){
		A<String> a = new A<String>();
		System.out.println(a.getString("ff"));
	}
}
