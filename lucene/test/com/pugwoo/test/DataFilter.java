package com.pugwoo.test;
/**
 * 2008年9月23日 下午04:56:46
 * @author Administrator
 *
 */
public class DataFilter {
	public static String getHTML(String sourcestr){
        if (sourcestr == null) {
            return "";
        }
        sourcestr = sourcestr.replaceAll("\\x26", "&amp;");//&
        sourcestr = sourcestr.replaceAll("\\x3c", "&lt;");//<
        sourcestr = sourcestr.replaceAll("\\x3e", "&gt;");//>
        sourcestr = sourcestr.replaceAll("\\x09", "&nbsp;&nbsp;&nbsp;&nbsp;");//tab��
        sourcestr = sourcestr.replaceAll("\\x20", "&nbsp;");//�ո�
        sourcestr = sourcestr.replaceAll("\\x22", "&quot;");//"

        sourcestr = sourcestr.replaceAll("\r\n", "<br>");//�س�����
        sourcestr = sourcestr.replaceAll("\r", "<br>");//�س�
        sourcestr = sourcestr.replaceAll("\n", "<br>");//����
        return sourcestr;
	}
}
