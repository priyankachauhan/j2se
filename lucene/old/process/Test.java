package process;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
/**
 *
 * @author Administrator
 */
public class Test {
    public static void main(String[] args){
/*        Document doc = new Document();
        Field bookNo = new Field("booknumber","FB309663004",Field.Store.YES,Field.Index.UN_TOKENIZED);
        Field bookName = new Field("bookname","钢铁是怎样炼成的",Field.Store.YES,Field.Index.TOKENIZED);
        Field author = new Field("author","Unknown",Field.Store.YES,Field.Index.UN_TOKENIZED);
        Field publishdate = new Field("publishdate","1999-01-01",Field.Store.YES,Field.Index.NO);
        Field bookabstract = new Field("abstract","钢铁是怎样炼成的，这部小说非常不错，是俄罗斯的小说",Field.Store.NO,Field.Index.TOKENIZED);
        Field price = new Field("price","25.00",Field.Store.YES,Field.Index.NO);

        doc.add(bookNo);
        doc.add(bookName);
        doc.add(author);
        doc.add(publishdate);
        doc.add(bookabstract);
        doc.add(price);*/

/*        Document doc1 = new Document();
        Field f1 = new Field("bookname","钢铁是怎样炼成的",Field.Store.YES,Field.Index.TOKENIZED);
        doc1.add(f1);
        Document doc2 = new Document();
        Field f2 = new Field("bookname","英雄女儿",Field.Store.YES,Field.Index.TOKENIZED);
        doc2.add(f2);
        Document doc3 = new Document();
        Field f3 = new Field("bookname","篱笆女人和狗",Field.Store.YES,Field.Index.TOKENIZED);
        doc3.add(f3);*/

        try{
            IndexWriter writer = new IndexWriter("D:\\indextest",new StandardAnalyzer(),true);
            writer.setUseCompoundFile(false);

            for(int i=1;i<10000;i++){
                Document doc = new Document();
                Field f = new Field("content","This is a test sentence.",Field.Store.YES,Field.Index.TOKENIZED);
                doc.add(f);
                writer.addDocument(doc);
            }
            
            writer.close();

     //       IndexSearcher searcher = new IndexSearcher("D:\\indextest");
     //       Term t = new Term("bookname","女");
     //       Query q = new TermQuery(t);
     //       Hits hits = searcher.search(q);
     //       for(int i=0; i<hits.length();i++){
     //           System.out.println(hits.doc(i));
     //       }
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
}
