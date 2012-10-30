package demo;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class Demo {

	public static void main(String[] args) throws Exception {

		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
		// Store the index in memory:
		Directory directory = new RAMDirectory();

		// To store an index on disk, use this instead:
		// Directory directory = FSDirectory.open("/tmp/testindex");
		IndexWriter iwriter = null;
		iwriter = new IndexWriter(directory, analyzer, true,
				new IndexWriter.MaxFieldLength(25000));

		Document doc = new Document();
		String title = "Chapter one";
		String text = "This is the text to be indexed.";
		doc.add(new Field("title", title, Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("fieldname", text, Field.Store.YES,
				Field.Index.ANALYZED));
		iwriter.addDocument(doc);
		iwriter.close();

		// Now search the index:
		IndexSearcher isearcher = new IndexSearcher(directory, true); // read-only=true
		// Parse a simple query that searches for "text":
		QueryParser parser = new QueryParser("fieldname", analyzer);
		Query query = parser.parse("text");
		ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
		// assertEquals(1, hits.length);
		// Iterate through the results:
		for (int i = 0; i < hits.length; i++) {
			Document hitDoc = isearcher.doc(hits[i].doc);
			// assertEquals("This is the text to be indexed.",
			// hitDoc.get("fieldname"));
			System.out.println("i="+i+",title:"+hitDoc.get("title"));
			System.out.println("i="+i+",name:"+hitDoc.get("fieldname"));
		}
		isearcher.close();
		directory.close();

	}

}
