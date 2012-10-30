package cn_analyzier;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 该类演示IKAnalyzer中文分词器
 * 
 * 部署：
 * 导入包IKAnalyzer3.2.5Stable.jar
 * 将ext_stopword.dic和IKAnalyzer.cfg.xml两个文件放到src目录下
 */
public class IKAnalyzerTest {

	//要测试的文字
	private static String text = "数学与计算科学学院,科计,吴红,谢博宇";
	
	public static void main(String[] args) {
		//生成一个StandardAnalyzer对象  ，这里使用IKAnalyzer
        Analyzer analyzer = new IKAnalyzer();  
        //测试字符串  
        StringReader reader = new StringReader(text);  
        //生成TokenStream对象  ，第一个参数是Field Name
        TokenStream stream = analyzer.tokenStream("name", reader);
        TermAttribute termAttribute = (TermAttribute) stream.getAttribute(TermAttribute.class);
        //循环读出
        int sum = 0;
        try {
			while(stream.incrementToken()){
				String token = termAttribute.term();
				System.out.println(token);
				sum ++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("一共有"+sum+"个token.");
	}

}
