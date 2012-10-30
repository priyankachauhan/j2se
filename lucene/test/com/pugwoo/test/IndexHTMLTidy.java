package com.pugwoo.test;
import java.io.File; 
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DecimalFormat;
import jeasy.analysis.MMAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.tidy.Tidy;

/**
 * 该类演示索引一个文件夹中的若干文件
 * 2011年1月13日 下午11:27:37
 */
public class IndexHTMLTidy {
	//要索引的文件的文件夹
	static String root = "H:\\J2SE\\lucene\\docs\\api\\demo";
	//索引存放的位置
	static String index = "C:\\lucene1552";
	
	static Document doc = null;
	static IndexWriter writer = null;

	public static void main(String[] args) throws Exception {
		//下面这个是中文分词库
		writer = new IndexWriter(index, new MMAnalyzer(), true);
		File f = new File(root);
		indexDocs(f);
		writer.optimize();
		writer.close();
		System.out.println("ok...");
	}

	// 递归调用
	public static void indexDocs(File f) throws Exception {
		if (f.isDirectory()) {
			File files[] = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				indexDocs(files[i]);
			}
		} else if (f.getName().endsWith(".html")) {
			indexDoc(f);
		}
	}

	// 索引一个文件
	public static void indexDoc(File f) throws Exception {
		doc = new Document();
		System.out.println(f.getPath());
		doc
				.add(new Field("path", f.getPath(), Field.Store.YES,
						Field.Index.NO));
		String size = new DecimalFormat("0000000000").format(f.length());
		doc.add(new Field("size", size, Field.Store.YES,
				Field.Index.UN_TOKENIZED));
		doc.add(new Field("lastmodified", DateTools.timeToString(f
				.lastModified(), DateTools.Resolution.DAY), Field.Store.YES,
				Field.Index.UN_TOKENIZED));

		Tidy tidy = new Tidy();
		tidy.setQuiet(true);
		tidy.setShowWarnings(false);
		// 乱码
		// org.w3c.dom.Document root = tidy.parseDOM(new FileInputStream(f),System.out);

		// 解决乱码问题
		// java.io.InputStream定义了抽象方法read(),从此输入流中读取一个数据字节。
		// java.io.FileInputStream实现了父类中定义的方法read()
		// public class InputStreamReader extends java.io.Reader
		// public class FileInputStream extends java.io.InputStream
		// InputStreamReader的方法read()-->读取单个字符。
		InputStreamReader ips = new InputStreamReader(new FileInputStream(f),"gb2312");
		// 适配器模式
		InputStream is = new ReaderToInputStream(ips);

		org.w3c.dom.Document root = tidy.parseDOM(is, null);
		// 得到根元素
		Element rawDoc = root.getDocumentElement();
		//得到title内容
		String title = getTitle(rawDoc);
		//得到body内容
		String body = getBody(rawDoc);
		
		System.out.println(title);        
		
		doc.add(new Field("title", title, Field.Store.YES,Field.Index.TOKENIZED));
		
		String summary = body;
		if (body.length() >= 200) {
			summary = body.substring(0, 200);
		}
		doc.add(new Field("summary", summary, Field.Store.YES,Field.Index.TOKENIZED));
		doc.add(new Field("content", body, Field.Store.NO,Field.Index.TOKENIZED));
		writer.addDocument(doc);
	}

	// 适配器
	public static class ReaderToInputStream extends InputStream {
		Reader reader;

		public ReaderToInputStream(Reader reader) {
			super();
			this.reader = reader;
		}

		@Override
		public int read() throws IOException {
			try {
				return reader.read();
			} catch (IOException e) {
				throw e;
			}
		}
	}

	// 得到title标签内容
	protected static String getTitle(Element rawDoc) {
		if (rawDoc == null) {
			return "";
		}
		String title = "";
		NodeList children = rawDoc.getElementsByTagName("title");
		if (children.getLength() > 0) {
			Element titleElement = ((Element) children.item(0));
			Text text = (Text) titleElement.getFirstChild();
			if (text != null) {
				title = text.getData();
			}
		}
		return title;
	}

	// 得到body标签内容
	protected static String getBody(Element rawDoc) {
		if (rawDoc == null) {
			return "";
		}
		String body = "";
		NodeList children = rawDoc.getElementsByTagName("body");
		if (children.getLength() > 0) {
			body = getText(children.item(0));
		}
		return body;
	}

	// 递归调用,因为标签里面还有标签
	protected static String getText(Node node) {
		NodeList children = node.getChildNodes();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			switch (child.getNodeType()) {
			case Node.ELEMENT_NODE:
				sb.append(getText(child));
				sb.append(" ");
				break;
			case Node.TEXT_NODE:
				sb.append(((Text) child).getData());
				break;
			}
		}
		return sb.toString();
	}
}
