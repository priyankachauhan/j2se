package com.pugwoo.test;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;

/**
 * 2008年9月23日 上午11:32:02
 * @author Administrator
 *
 */
public class TestBooleanQuery {
	public static void main(String[] args) throws Exception{
		String index = "D:\\share\\tools\\apache-tomcat-6.0.14\\apache-tomcat-6.0.14\\index_cn";
		IndexSearcher searcher = new IndexSearcher(index);
		Hits hits = null;
		TermQuery term1 = new TermQuery(new Term("title","ʹ��"));
		TermQuery term2 = new TermQuery(new Term("title","�ӿ�"));
		BooleanQuery query = new BooleanQuery();
		query.add(term1, BooleanClause.Occur.MUST);
		query.add(term2,BooleanClause.Occur.MUST_NOT);
		hits = searcher.search(query);
		
		System.out.println("total:"+hits.length());

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
