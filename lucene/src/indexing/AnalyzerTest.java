package indexing;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.util.Version;

/**
 * 该类演示StandarAnlyzer分词作用
 */
public class AnalyzerTest {

	/**
	 * 下面是2.0旧版本的
	 */
	@SuppressWarnings("deprecation")
	public static void token2() {
		//生成一个StandardAnalyzer对象  
        Analyzer aAnalyzer = new StandardAnalyzer(Version.LUCENE_29);  
        //测试字符串  
        StringReader sr = new StringReader("lighter javaeye com is the are on");  
        //生成TokenStream对象  
        TokenStream ts = aAnalyzer.tokenStream("name", sr);   
        try {  
            int i=0;  
            Token t = ts.next();  
            while(t!=null)  
            {  
                //辅助输出时显示行号  
                i++;  
                //输出处理后的字符  
                System.out.println("第"+i+"行:"+t.termText());  
                //取得下一个字符  
                t=ts.next();  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 

	}

	/**
	 * 下面是新版本的
	 */
	public static void main(String[] args) {
		//生成一个StandardAnalyzer对象  
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_29);  
        //测试字符串  
        StringReader reader = new StringReader("lighter javaeye com is the are on");  
        //生成TokenStream对象  ，第一个参数是Field Name
        TokenStream stream = analyzer.tokenStream("name", reader);
        TermAttribute termAttribute = (TermAttribute) stream.getAttribute(TermAttribute.class);
        //循环读出
        try {
			while(stream.incrementToken()){
				String token = termAttribute.term();
				System.out.println(token);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
