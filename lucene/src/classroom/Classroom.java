package classroom;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class Classroom {

	public static void main(String[] args) throws Exception{
		
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_29);
		Directory directory = new RAMDirectory();
		
		IndexWriter indexWriter = new IndexWriter(directory, analyzer, true,
				new IndexWriter.MaxFieldLength(10000));
		
		indexWriter.addDocument(getActivity("23", "上课:计算机网络",
				"教师:吴红,班级:数学与计算科学学院信息与计算科学2006级B班"));
		indexWriter.addDocument(getActivity("26", "讲座:计算机概念设计",
		        "演讲:王五,主办方:数学与计算科学学院团委"));
		
		indexWriter.close();
		
		//索引
		query(directory, analyzer, "上课");

	}
	
	private static Document getActivity(String id, String name, String user){
		Document document = new Document();
		Field f_id = new Field("id",id,Field.Store.YES, Field.Index.NOT_ANALYZED);
		Field f_name = new Field("name", name, Field.Store.YES, Field.Index.ANALYZED);
		Field f_user = new Field("user", user, Field.Store.YES, Field.Index.ANALYZED);
		
		document.add(f_id);
		document.add(f_name);
		document.add(f_user);
		
		return document;
	}
	
	private static void query(Directory directory, Analyzer analyzer, String queryString)
	        throws Exception{
		IndexSearcher indexSearcher = new IndexSearcher(directory, true);
		QueryParser queryParser = new QueryParser(Version.LUCENE_29, "user", analyzer);
		Query query = queryParser.parse(queryString);
		
		TopScoreDocCollector collector = TopScoreDocCollector.create(100, false);
		indexSearcher.search(query, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		for(int i=0; i<hits.length; i++){
			Document document = null;
			try {
				document = indexSearcher.doc(hits[i].doc);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(document.getField("user"));
		}
		
		//查看所有匹配的个数，注意前面只取出了前100个
		System.out.println("Total hits: " + collector.getTotalHits());
		
	}

}
