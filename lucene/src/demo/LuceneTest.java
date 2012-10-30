package demo;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiSearcher;
import org.apache.lucene.search.Query;
@SuppressWarnings("deprecation")
public class LuceneTest {
	public static void main(String[] args) {   
        try {   
            LuceneTest luceneTest = new LuceneTest();   
            // 创建索引   
            luceneTest.index();   
            // 在索引所在目录下搜索"中国 金牌"   
            luceneTest.search("中国 金牌");   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
        System.out.println("ok");   
    }
	
	public void index() throws Exception {   
        /* 创建索引初始化，执行这些语句将创建或清空d:\\save\\目录下所有索引 */   
        IndexWriter writer1 = new IndexWriter("c:\\lucene\\",   
                new StandardAnalyzer(), true);   
        writer1.close();   
    
        /*  
         * 往创建的初始化索引中添加索引内容，StandardAnalyzer表示用lucene自带的标准分词机制，  
         * false表示不覆盖原来该目录的索引，细心的读者可能已经发现， 这句话和上面的那句就这个false不一样  
         */   
        IndexWriter writer2 = new IndexWriter("c:\\lucene\\",   
                new StandardAnalyzer(), false);   
        /* 创建一份文件 */   
        Document doc1 = new Document();   
        /*  
         * 创建一个域ArticleTitle，并往这个域里面添加内容 "Field.Store.YES"表示域里面的内容将被存储到索引  
         * "Field.Index.TOKENIZED"表示域里面的内容将被索引，以便用来搜索  
         */   
        Field field1 = new Field("ArticleTitle", "北京2008年奥运会", Field.Store.YES,   
                Field.Index.TOKENIZED);   
        /* 往文件里添加这个域 */   
        doc1.add(field1);   
        /* 同理：创建另外一个域ArticleText，并往这个域里面添加内容 */   
        Field field2 = new Field("ArticleText", "这是一届创造奇迹、超越梦想的奥运会.......",   
                Field.Store.YES, Field.Index.TOKENIZED);   
        doc1.add(field2);   
        // 在这里还可以添加其他域   
        /* 添加这份文件到索引 */   
        writer2.addDocument(doc1);   
    
        /* 同理：创建第二份文件 */   
        Document doc2 = new Document();   
        field1 = new Field("ArticleTitle", "中国获得全球赞誉", Field.Store.YES,   
                Field.Index.TOKENIZED);   
        doc2.add(field1);   
        field2 = new Field("ArticleText", "中国所取得的金牌总数排行榜的榜首........",   
                Field.Store.YES, Field.Index.TOKENIZED);   
        doc2.add(field2);   
    
        writer2.addDocument(doc2);   
    
        // 在这里可以添加其他文件   
    
        /* 关闭 */   
        writer2.close();   
    }   
	
	public void search(String serchString) throws Exception {   
        /* 创建一个搜索，搜索刚才创建的d:\\save\\目录下的索引 */   
        
		IndexSearcher indexSearcher = new IndexSearcher("c:\\lucene\\");   
        /* 在这里我们只需要搜索一个目录 */   
        IndexSearcher indexSearchers[] = { indexSearcher };   
        /* 我们需要搜索两个域"ArticleTitle", "ArticleText"里面的内容 */   
        String[] fields = { "ArticleTitle", "ArticleText" };   
        /* 下面这个表示要同时搜索这两个域，而且只要一个域里面有满足我们搜索的内容就行 */   
        BooleanClause.Occur[] clauses = { BooleanClause.Occur.SHOULD,   
                BooleanClause.Occur.SHOULD };   
        /*  
         * MultiFieldQueryParser表示多个域解析，  
         * 同时可以解析含空格的字符串，如果我们搜索"中国 金牌"，根据前面的索引，显然搜到的是第二份文件  
         */   
        Query query = MultiFieldQueryParser.parse(serchString, fields, clauses,   
                new StandardAnalyzer());   
        /* Multisearcher表示多目录搜索，在这里我们只有一个目录 */   
        MultiSearcher searcher = new MultiSearcher(indexSearchers);   
        /* 开始搜索 */   
        Hits h = searcher.search(query);   
        /* 把搜索出来的所有文件打印出来 */   
        for (int i = 0; i < h.length(); i++) {   
            /* 打印出文件里面ArticleTitle域里面的内容 */   
            System.out.println(h.doc(i).get("ArticleTitle"));   
            /* 打印出文件里面ArticleText域里面的内容 */   
            System.out.println(h.doc(i).get("ArticleText"));   
        }   
        /* 关闭 */   
        searcher.close();   
    }   
}
