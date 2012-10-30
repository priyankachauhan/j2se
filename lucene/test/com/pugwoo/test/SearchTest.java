package com.pugwoo.test;
import java.io.IOException;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

/**
 * 2011年1月13日 下午11:32:25
 * @author Administrator
 *
 */
public class SearchTest {
	public static void main(String[] args) throws Exception {
		String index = "D:\\share\\0400_Servlet_JSP\\soft\\apache-tomcat-5.5.17\\apache-tomcat-5.5.17\\index_cn";
		IndexSearcher searcher=null;
		try {
			searcher = new IndexSearcher(index);
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Query query = new TermQuery(new Term("title","使用"));
//		QueryParser parser = new QueryParser("title",new MMAnalyzer());
//		parser.setDefaultOperator(QueryParser.AND_OPERATOR);
//		Query query = parser.parse("使用 软件包");
		
		
		Hits hits = searcher.search(query);
		System.out.println(hits.length());
		for (int i = 0; i < hits.length(); i++) {
			Document doc = hits.doc(i);
			String title = doc.get("title");
			System.out.println(title);
		}
		searcher.close();
	}
}
