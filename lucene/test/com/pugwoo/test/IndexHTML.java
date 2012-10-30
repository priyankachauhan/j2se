package com.pugwoo.test;
import java.io.File;
import java.io.FileReader;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
/**
 * 演示索引一个文件
 * 2011年1月13日 下午11:18:33
 */
public class IndexHTML {
	public static void main(String[] args) throws Exception {
		//root是要索引的文件的位置
		String root = "H:\\J2SE\\lucene\\docs\\api\\index.html";
		//index是index文件存放的位置
		String index = "C:\\lucece155";
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(index, new StandardAnalyzer(),
					true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		File f = new File(root);
		Document doc = new Document();
		doc.add(new Field("path",f.getPath(),Field.Store.YES,Field.Index.NO));
		doc.add(new Field("title","我们是共产主义接班人",Field.Store.YES,Field.Index.TOKENIZED));
		doc.add(new Field("size","000129",Field.Store.YES,Field.Index.UN_TOKENIZED));
		doc.add(new Field("content",new FileReader(f)));
		
		writer.addDocument(doc);
		writer.optimize();
		writer.close();

	}
}
