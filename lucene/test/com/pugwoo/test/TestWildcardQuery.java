package com.pugwoo.test;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.WildcardQuery;
 
/**
 * 2008年9月23日 上午11:32:02
 * ͨ���������*���0������ַ�,?���һ����һ���ַ�
 */
public class TestWildcardQuery {
	public static void main(String[] args) throws Exception{
		String index ="D:\\share\\TOOLS\\apache-tomcat-5.5.17\\apache-tomcat-5.5.17\\webapps\\index"; 
		IndexSearcher searcher = new IndexSearcher(index);
		Hits hits = null;
		
		Query query = new WildcardQuery(new Term("title", "?ucli*"));
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
