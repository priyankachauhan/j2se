package com.pugwoo.test;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.WildcardQuery;

 /**
  * 2011年1月13日 下午11:40:00
  * @author Administrator
  *
  */
public class TestQueryParser {
	public static void main(String[] args) throws Exception{
		String index ="C:\\lucece155"; 
		IndexSearcher searcher = new IndexSearcher(index);
		Hits hits = null;
		
		QueryParser parser = new QueryParser("title",new StandardAnalyzer());
		Query query = parser.parse("lucene+java");
		System.out.println(query.toString());
		hits = searcher.search(query);

		for (int i = 0; i < hits.length(); i++) {
			Document doc = hits.doc(i);
			String title = doc.get("title");
			String size = doc.get("size");
			System.out.println(title+"..."+size);
			if (i>20){
				System.out.println("...");
				break;
			}
		}
		searcher.close();	
	}
}
