package com.pugwoo.dom4j;

import java.io.IOException;
import java.util.Iterator;

import junit.framework.TestCase;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

public class GetStarted extends TestCase {

	/**
	 * 解析XML
	 * 
	 * @throws DocumentException
	 */
	public void parseXML() throws DocumentException {
		// 获得Document对象
		Document document = Utils.getDocument("/myxml.xml");
		Utils.print(document);

		/**
		 * 获得Element的几种方法
		 */
		// 1.通过Document对象获得根节点
		Element root = document.getRootElement();
		System.out.println("根节点名称:" + root.getName());
		// 2.通过当前Element的子元素的名称获得Element
		Element book1 = root.element("book");
		System.out.println("通过子节点的Tag名获得：" + book1.getUniquePath());
		// 3.通过ID获得Element，注意ID必须是大写的
		Element book1_name = document.elementByID("boo1_name");
		System.out.println("通过ID获得：" + book1_name.getUniquePath());
		// 4.通过当前Element元素遍历其子元素
		System.out.print("遍历当前元素的子元素:");
		for (Iterator iter = root.elementIterator(); iter.hasNext();) {
			Element element = (Element) iter.next();
			System.out.print(element.getName() + " ");
		}
		System.out.println();
		System.out.println();

		/**
		 * 对于Element的信息获取
		 */
		// 1. 获得标签Tag名称
		System.out.println("获得Element的Tag: " + root.getName());
		// 2. 获得属性值
		System.out.println("获得Element的属性值：" + root.attributeValue("owner"));
		// 3. 获得一个Element的绝对地址
		System.out.println("获得Element的绝对地址:" + book1.getUniquePath());
		// 4. 获得Element中的值
		System.out.println("获得Element中的值:" + book1_name.getText());

		/**
		 * 通过XPath获得Node对象
		 */
		Node booknode = document.selectSingleNode("/books/book[1]");
		System.out.println("通过Xpath获得Node对象:" + booknode.getName());
	}

	/**
	 * 将Document对象写入到XML文件中
	 * @throws IOException
	 */
	public void writeXML() throws IOException {
		// 新建Document
		Document document = DocumentHelper.createDocument();
		// 创建Root结点
		Element root = document.addElement("root");

		@SuppressWarnings("unused")
		Element element1 = root.addElement("user").addAttribute("name",
				"Alexander")
				.addAttribute("blog", "http://www.pugwoo.com").addText(
						"我是中文的名字");
		
		// 这个位置不知道怎么设置到classpath下
		Utils.writeDocument(document, "test1.xml");
	}
}
