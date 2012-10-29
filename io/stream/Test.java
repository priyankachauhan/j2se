import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Test {

	public static void main(String[] args) throws Exception {

		InputStream nin = new URL("http://www.baidu.com").openStream();
		InputStreamReader ireader = new InputStreamReader(nin, "gb2312");
		BufferedReader breader = new BufferedReader(ireader);
		String str;
		do {
			str = breader.readLine();
			System.out.println(str);
		} while (str != null);

		
	}

}
