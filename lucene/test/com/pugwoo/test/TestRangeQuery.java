package com.pugwoo.test;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.RangeQuery;
import org.apache.lucene.search.TermQuery;

 /**
  * 2008年9月23日 上午11:32:02
  * @author Administrator
  *
  */
public class TestRangeQuery {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		String index = "C:\\tomcat\\webapps\\index";
		IndexSearcher searcher = new IndexSearcher(index);
		Hits hits = null;
		
		Term begin = new Term("size","0000000001");
		Term end = new Term("size","0000001000");
		RangeQuery query = new RangeQuery(begin,end,true);
		System.out.println(query.toString());
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
