package com.pugwoo.test;
 
/**
 * 2008年9月23日 上午11:32:02
 */
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RangeQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.spans.SpanFirstQuery;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanNotQuery;
import org.apache.lucene.search.spans.SpanOrQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.store.RAMDirectory;

public class TestSpanTermQuery {
	
	static String index = "C:\\tomcat\\webapps\\index";
	static IndexSearcher searcher = null;
	static Hits hits = null;
	

	/**
	 * ��ĳ�ֿ�ȷ�Χ�ڣ����ҹؼ�ʲ�ƥ���ĵ�����Ϊ�������
	 * There are five subclasses of the base SpanQuery,
	 * SpanNotQuery
	 * SpanOrQuery
	 */
	public static void testSpanTermQuery()throws Exception{
		String s = "Man always remember love because of romance only";
		RAMDirectory directory = new RAMDirectory();
		IndexWriter writer = new IndexWriter(directory,new StandardAnalyzer(),true);
		Document doc = new Document();
		doc.add(new Field("title",s,Field.Store.YES,Field.Index.TOKENIZED));
		writer.addDocument(doc);
		writer.close();

		searcher = new IndexSearcher(directory);
		
		//SpanTermQuery������ɵļ���Ч���TermQuery��ȫһ��
		//�����ڲ�����¼��һЩλ����Ϣ����Щ��Ϣ����SpanQuery���������API��ʹ�á�
		Term t = new Term("title","remember");
		SpanTermQuery query = new SpanTermQuery(t);

		hits = searcher.search(query);
	}
	//��������3������Լ������ĵ���
	//���������2�����޷��������ĵ���
	public static void testSpanFirstQuery()throws Exception{
		String s = "Man always remember love because of romance only";
		RAMDirectory directory = new RAMDirectory();
		IndexWriter writer = new IndexWriter(directory,new StandardAnalyzer(),true);
		Document doc = new Document();
		doc.add(new Field("title",s,Field.Store.YES,Field.Index.TOKENIZED));
		writer.addDocument(doc);
		writer.close();

		searcher = new IndexSearcher(directory);
		SpanTermQuery brown = new SpanTermQuery(new Term("title", "remember"));
		SpanFirstQuery query = new SpanFirstQuery(brown, 3);
		hits = searcher.search(query);
	}
	//SpanNearQuery�Ĺ��췽���е�2��������PhraseQuery���¶ȵĸ�����ͬ��
	//��2��term֮������޹صĴʵ�������
	//��3�������ʾ�����е�ÿ�����Ƿ�һ��Ҫ����˳��������ĵ��С�
	//����2�������Ϊ2��������������ĵ��ˡ�
	public static void testSpanNearQuery()throws Exception{
		String s = "Man always remember love because of romance only";
		RAMDirectory directory = new RAMDirectory();
		IndexWriter writer = new IndexWriter(directory,new StandardAnalyzer(),true);
		Document doc = new Document();
		doc.add(new Field("title",s,Field.Store.YES,Field.Index.TOKENIZED));
		writer.addDocument(doc);
		writer.close();

		searcher = new IndexSearcher(directory);
		SpanTermQuery brown = new SpanTermQuery(new Term("title", "remember"));
		Term t1 = new Term("title","Man");
		Term t2 = new Term("title","because");
		SpanTermQuery q1 = new SpanTermQuery(t1);
		SpanTermQuery q2 = new SpanTermQuery(t2);
		SpanNearQuery query = new SpanNearQuery(new SpanQuery [ ] {q1,q2},2,false);
		
		hits = searcher.search(query);
	}
	public static void testSpanNearQueryNested()throws Exception{
		String s = "aa bb cc dd ee ff gg hh ii jj kk";
		RAMDirectory directory = new RAMDirectory();
		IndexWriter writer = new IndexWriter(directory,new StandardAnalyzer(),true);
		Document doc = new Document();
		doc.add(new Field("title",s,Field.Store.YES,Field.Index.TOKENIZED));
		writer.addDocument(doc);
		writer.close();

		searcher = new IndexSearcher(directory);
		Term t1 = new Term("title","aa");
		Term t2 = new Term("title","cc");
		SpanTermQuery s1 = new SpanTermQuery(t1);
		SpanTermQuery s2 = new SpanTermQuery(t2);
		Term t3 = new Term("title","gg");
		Term t4 = new Term("title","kk");
		SpanTermQuery s3 = new SpanTermQuery(t3);
		SpanTermQuery s4 = new SpanTermQuery(t4);
		SpanNearQuery query1 = new SpanNearQuery(new SpanQuery [] {s1,s2},1,false);
		SpanNearQuery query2 = new SpanNearQuery(new SpanQuery [] {s3,s4},3,false);
		SpanNearQuery query = new SpanNearQuery(new SpanQuery [] {query1,query2},3,false);

		hits = searcher.search(query);
	}
	
	//SpanOrQuery���ǰ�����SpanQuery�Ľ���ۺ���������Ϊ��ļ������
	public static void testSpanOrQuery()throws Exception{
		String s = "aa bb cc dd ee ff gg hh ii jj kk";
		RAMDirectory directory = new RAMDirectory();
		IndexWriter writer = new IndexWriter(directory,new StandardAnalyzer(),true);
		Document doc = new Document();
		doc.add(new Field("title",s,Field.Store.YES,Field.Index.TOKENIZED));
		writer.addDocument(doc);
		writer.close();

		searcher = new IndexSearcher(directory);
		Term t1 = new Term("title","aa");
		Term t2 = new Term("title","cc");
		SpanTermQuery s1 = new SpanTermQuery(t1);
		SpanTermQuery s2 = new SpanTermQuery(t2);
		Term t3 = new Term("title","ff");
		Term t4 = new Term("title","jj");
		SpanTermQuery s3 = new SpanTermQuery(t3);
		SpanTermQuery s4 = new SpanTermQuery(t4);
		SpanNearQuery query1 = new SpanNearQuery(new SpanQuery [] {s1,s2},1,false);
		SpanNearQuery query2 = new SpanNearQuery(new SpanQuery [] {s3,s4},3,false);
		SpanOrQuery query = new SpanOrQuery(new SpanQuery [] {query1,query2});

		hits = searcher.search(query);
	}
	//SpanNotQuery������SpanQuery���������ʾ�ĺ����ǣ�
	//�ӵ�һ��SpanQuery�Ĳ�ѯ����У�ȥ���ڶ���SpanQuery�Ĳ�ѯ���
	//ע�⣺SpanNotQuery���ų���ЩSpanQuery�����ཻ�����ĵ�
	public static void testSpanNotQuery()throws Exception{
		String s = "aa bb cc dd ee ff gg hh ii jj kk";
		String ss = "gg aa bb cc dd ee ff hh ii ii jj kk";
		RAMDirectory directory = new RAMDirectory();
		IndexWriter writer = new IndexWriter(directory,new StandardAnalyzer(),true);
		
		Document doc = new Document();
		doc.add(new Field("title",s,Field.Store.YES,Field.Index.TOKENIZED));
		writer.addDocument(doc);
		
		Document doc1 = new Document();
		doc1.add(new Field("title",ss,Field.Store.YES,Field.Index.TOKENIZED));
		writer.addDocument(doc1);
		
		writer.optimize();
		writer.close();

		searcher = new IndexSearcher(directory);
		
		Term t1 = new Term("title","gg");
		SpanTermQuery s1 = new SpanTermQuery(t1);
		SpanFirstQuery query1 = new SpanFirstQuery(s1,9);
		Term t3 = new Term("title","ff");
		Term t4 = new Term("title","jj");
		SpanTermQuery s3 = new SpanTermQuery(t3);
		SpanTermQuery s4 = new SpanTermQuery(t4);
		SpanNearQuery query2 = new SpanNearQuery(new SpanQuery [] {s3,s4},3,false);
		SpanNotQuery query = new SpanNotQuery(query1,query2);
		
		System.out.println(query.toString());
		hits = searcher.search(query);
		//System.out.println(query1.toString());
		//hits = searcher.search(query1);
		//System.out.println(query2.toString());
		//hits = searcher.search(query2);
	}
	


	public static void main(String[] args) throws Exception {
		searcher = new IndexSearcher(index);
		//testSpanTermQuery();
		//testSpanFirstQuery();
		//testSpanNearQuery();
		//testSpanNearQueryNested();

		//testSpanNotQuery();
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
