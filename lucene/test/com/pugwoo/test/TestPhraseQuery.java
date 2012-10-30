package com.pugwoo.test;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.store.RAMDirectory;

 /**
  * 2008年9月23日 上午11:32:02
  * @author Administrator
  *
  */
public class TestPhraseQuery {
	public static void main(String[] args) throws Exception{
		String index ="D:\\share\\TOOLS\\apache-tomcat-5.5.17\\apache-tomcat-5.5.17\\webapps\\index"; 
		IndexSearcher searcher = new IndexSearcher(index);
		Hits hits = null;
		
		String s = "the quick brown fox jumped over the lazy dog.";
		RAMDirectory directory = new RAMDirectory();
		IndexWriter writer = new IndexWriter(directory,new StandardAnalyzer(),true);
		Document doc = new Document();
		doc.add(new Field("field",s,Field.Store.YES,Field.Index.TOKENIZED));
		writer.addDocument(doc);
		writer.close();
		
		searcher = new IndexSearcher(directory);
		PhraseQuery query = new PhraseQuery();
		query.setSlop(3);
		query.add(new Term("field", "fox"));
		query.add(new Term("field", "quick"));
		hits = searcher.search(query);
		
		System.out.println("total:"+hits.length());

		for (int i = 0; i < hits.length(); i++) {
			Document doc1 = hits.doc(i);
			String title = doc1.get("title");
			String size = doc1.get("size");
			System.out.println(title+"..."+size);
			if (i>20){
				System.out.println("...");
				break;
			}
		}
		searcher.close();	
	}
}
