package com.pugwoo.test;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.store.RAMDirectory;
 
/**
 * 2008年9月23日 上午11:32:02
 * @author Administrator
 *
 */
public class TtestMultiPhraseQuery {
	public static void main(String[] args) throws Exception{
		String index ="D:\\share\\TOOLS\\apache-tomcat-5.5.17\\apache-tomcat-5.5.17\\webapps\\index"; 
		IndexSearcher searcher = new IndexSearcher(index);
		Hits hits = null;
		
		RAMDirectory directory = new RAMDirectory();
		IndexWriter writer = new IndexWriter(directory,new StandardAnalyzer(),true);

		Document doc1 = new Document();
		Document doc2 = new Document();
		Document doc3 = new Document();
		Document doc4 = new Document();
		Document doc5 = new Document();
		Document doc6 = new Document();
		
		String s1 = "�������������ɵ�";
		String s2 = "����սʿ";
		String s3 = "�ֺ��������ֽ���Ԫ��";
		String s4 = "��Ҫ�����и���̼Ԫ��";
		String s5 = "��͸���������Ҫ�Ľ���";
		String s6 = "�����������Ҫ�Ľ���";
		
		Field f1 = new Field("title",s1,Field.Store.YES,Field.Index.TOKENIZED);
		Field f2 = new Field("title",s2,Field.Store.YES,Field.Index.TOKENIZED);
		Field f3 = new Field("title",s3,Field.Store.YES,Field.Index.TOKENIZED);
		Field f4 = new Field("title",s4,Field.Store.YES,Field.Index.TOKENIZED);
		Field f5 = new Field("title",s5,Field.Store.YES,Field.Index.TOKENIZED);
		Field f6 = new Field("title",s6,Field.Store.YES,Field.Index.TOKENIZED);
		
		doc1.add(f1);
		doc2.add(f2);
		doc3.add(f3);
		doc4.add(f4);
		doc5.add(f5);
		doc6.add(f6);
		
		writer.addDocument(doc1);
		writer.addDocument(doc2);
		writer.addDocument(doc3);
		writer.addDocument(doc4);
		writer.addDocument(doc5);
		writer.addDocument(doc6);
		
		writer.close();
		
		searcher = new IndexSearcher(directory);
		MultiPhraseQuery query = new MultiPhraseQuery();

		//title:"�� (�� �� Ҫ)"
		query.add(new Term("title","��"));
		Term t1 = new Term("title","��");
		Term t2 = new Term("title","��");
		Term t3 = new Term("title","Ҫ");
		query.add(new Term [] {t1,t2,t3});

		
		//title:"(�� ��) ��"		
/*		Term t1 = new Term("title","��");
		Term t2 = new Term("title","��");
		query.add(new Term [] {t1,t2});
		query.add(new Term("title","��"));
*/		
		

		//title:"(�� ��) �� (�� ս)"
/*		Term t1 = new Term("title","��");
		Term t2 = new Term("title","��");
		query.add(new Term [] {t1,t2});

		query.add(new Term("title","��"));

		Term t3 = new Term("title","��");
		Term t4 = new Term("title","ս");
		query.add(new Term [] {t3,t4});
*/
		
	
		System.out.println(query.toString());
		hits = searcher.search(query);
		
		System.out.println("total:"+hits.length());

		for (int i = 0; i < hits.length(); i++) {
			Document docA = hits.doc(i);
			String title = docA.get("title");
			String size = docA.get("size");
			System.out.println(title+"..."+size);
			if (i>20){
				System.out.println("...");
				break;
			}
		}
		searcher.close();	
	}
}
