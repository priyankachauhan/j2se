package com.pugwoo.myjson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 自己写的一个简单的Json解析器，只需要很简单的功能： 它不进行任何类型的转换，全部看作String型 不进行Bean的创建\
 * 根据http://www.json.org/设计
 * 强制规定Object的Name不包含:
 */
public class MyJson {

	/**
	 * Json只有两种数据类型： 1)对象Object类型，形如{id:123,name:'3432'}
	 * 2)数组Array类型，形如[{},[],,{}]
	 */

	// 存放Object类型
	private Map<String, MyJson> map;

	// 存放Array类型
	private List<MyJson> list;

	// 存放单值类型
	private String value;

	// 存放json数据
	private String json;
	
	public MyJson(){
	}
	public MyJson(String json){
		fromJson(json);
	}

	public String toString(){
		if(map != null){
			return map.toString();
		}else if(list != null){
			return list.toString();
		}else if(value != null){
			return value;
		}else return null;
	}
	
	@SuppressWarnings("unchecked")
	public String toJson(){
		StringBuffer sb = new StringBuffer();
		if(map != null){
			sb.append('{');
			Set keys = map.keySet();
			Iterator<String> iter = keys.iterator();
			if(iter.hasNext()){ //先处理第一个
				String key = iter.next(); //约定ObjectName不包含冒号
				sb.append(key).append(':').append(map.get(key).toJson());
			}
			while(iter.hasNext()){
				String key = iter.next(); //约定ObjectName不包含冒号
				sb.append(',').append(key).append(':').append(map.get(key).toJson());
			}
			sb.append('}');
		} else if(list != null){
			sb.append('[');
			if(list.size() > 0){
				sb.append(list.get(0).toJson()); //先处理第一个
			}
			for(int i=1; i<list.size(); i++){
				sb.append(',').append(list.get(i).toJson());
			}
			sb.append(']');
		} else if(value != null){
			sb.append(value);
		} else sb.append("null");
		return sb.toString();
	}
	public void fromJson(String json) {
		if(json == null)
			return;
		else if(json.isEmpty()){
			this.value = json;
			return;
		}
		this.json = json;
		char firstChar = json.charAt(0);
		if(firstChar == '{')
			getMap(0, this);
		else if(firstChar == '[')
			getList(0, this);
		else if(firstChar == '\'' || firstChar == '"'){
			StringBuffer string = new StringBuffer();
			getString(0, string);
			this.value = string.toString();
		}else{
			StringBuffer string = new StringBuffer();
			getStringWithoutQuota(0, string);
			this.value = string.toString();
		}
	}

	/**
	 * 实用工具
	 */
	public MyJson get(int index){
		if(list == null)
			return null;
		return list.get(index);
	}
	public MyJson get(String key){
		if(map == null)
			return null;
		return map.get(key);
	}
	public String getValue(){
		return value;
	}
	/**
	 * 一般从EXTJS传过来的Json字符串都是加双引号的，这里只处理这种情况
	 */
	public String getValueFromExtjsForSQL(){
		if(value == null || value.isEmpty())
			return value;
		if(value.charAt(0) != '"')
			return value;
		String content = value.substring(1, value.length()-1);
		content = content.replace("'", "''"); //这两行程序效率有待提升
		content = content.replace("\\\"", "\"");
		return new StringBuffer("'").append(content).append("'").toString();
	}
	public Set<String> getMapKeySet(){
		if(map == null)
			return null;
		return map.keySet();
	}
	public boolean isList(){
		return list != null;
	}
	public boolean isMap(){
		return map != null;
	}
	public boolean isValue(){
		return value != null;
	}
	/**
	 * pos指向"["，返回处理完这个[]的结束位置，指向“]”的下一位，把这个list放置到_myjson里面
	 */
	private int getList(int pos, MyJson myjson){
		boolean notEnd = true;
//		if(json.charAt(_pos) != '[')
//		throw new Exception("Json Syntax Error.");
		List<MyJson> list = new ArrayList<MyJson>();
		myjson.list = list;
		pos++;
//		if(_pos >= json.length())
//			throw new Exception("Json Syntax Error.");
		if(json.charAt(pos) == ']') //空的
			return pos+1;
		while(notEnd){
			MyJson tmpJson = new MyJson();
			pos = getValue(pos, tmpJson, ',', ']');
			list.add(tmpJson);
			if(json.charAt(pos) == ']')
				notEnd = false;
			else pos++;
		}
		return pos+1;
	}
	
	/**
	 * pos指向"{"，返回处理完这个{}的结束位置，指向“}”的下一位，把这个map放置到_myjson里面
	 */
	private int getMap(int pos, MyJson myjson){
		boolean notEnd = true;
//		if(json.charAt(_pos) != '{')
//		throw new Exception("Json Syntax Error.");
		Map<String, MyJson> tmpMap = new HashMap<String, MyJson>();
		myjson.map = tmpMap;
		pos++;
		//if(pos >= json.length())
		//	throw new Exception("Json Syntax Error.");
		if(json.charAt(pos) == '}') //空的
			return pos+1;
		while(notEnd){
			StringBuffer name = new StringBuffer();
			pos = getObjectName(pos, name); //此时_pos指向冒号后面
			MyJson tmpJson = new MyJson();
			pos = getValue(pos, tmpJson, ',', '}'); //此时pos指向,或}，这里忽略了语法错误，还有引号后面是没有空格的情况
			tmpMap.put(name.toString(), tmpJson);
			if(json.charAt(pos) == '}')
				notEnd = false;
			else pos++;
		}
		return pos+1;
	}
	
	/**
	 * 获得Object的Name,pos执行name的第一个字母，获得的string将去除单引号和双引号
	 * 返回的pos指向冒号:的下一位
	 */
	private int getObjectName(int pos, StringBuffer string){
		int first = pos;
		char firstChar = json.charAt(first);
		if(firstChar == '\'' || firstChar == '"'){
			StringBuffer tmp = new StringBuffer();
			pos = getString(pos, tmp);
			string.append(tmp.substring(1, tmp.length()-1)); //不考虑语法错误，如果错误将抛出出界异常
			//if(json.charAt(pos) == ':')
				return pos+1; //为了效率，不考虑语法错误
			//else throw new Exception("Json Syntax Error.");
		} else {
			pos = getStringWithoutQuota(pos, string, ':');
			//if(json.charAt(pos-1) != ':')
			//	throw new Exception("Json Syntax Error.");//为了效率，不考虑语法错误
			//else return pos;
			return pos+1;
		}
	}

	/**
	 * 获得Json中定义的值，其值可能是字符串也可能是数据，或Map或List!,如果是字符串，保存其原汁原味
	 * _pos指向值的第一位，返回处理完Value后的下一位
	 * 如果有设置endChars，则返回的位置指向endChars或\0
	 */
	private int getValue(int pos, MyJson myjson, char... endChars){
		char firstChar = json.charAt(pos);
		StringBuffer string = new StringBuffer();
		if(firstChar == '\'' || firstChar == '"'){ //字符串
			pos = getString(pos, string);
			myjson.value = string.toString();
			return pos;
		} else if(firstChar == '{'){ //Map
			return getMap(pos, myjson);
		} else if(firstChar == '['){ //List
			return getList(pos, myjson);
		}
		else{
			pos = getStringWithoutQuota(pos, string, endChars);
			myjson.value = string.toString();
			return pos;
		}
	}
	/**
	 * 设置由单引号或双引号引起来字符串，_pos指向字符串的第一个,第一个char必须为'或"
	 * 返回值指向单引号和双引号的下一个字符
	 * 该方法获得的是整个String，包括括号，原汁原味
	 */
	private int getString(int pos, StringBuffer string) {
		boolean flag = false; // 指示前一个字符是不是转义字符\
		boolean notEnd = true; // 标志字符串是否已经结束
		int first = pos; // 保存第一个位置
		char firstChar = json.charAt(pos); // 第一个char
//		if (firstChar != '\'' && firstChar != '"') // 第一个char必须为'或"
//			throw new Exception("Json Syntax Error."); //这个异常可以由上面调用的方法避免，因此不用出现
		while (notEnd) {
			++pos;
			if (!flag) {// 如果前一个不是转义字符
				char curChar = json.charAt(pos);
				if (curChar == firstChar)
					notEnd = false;
				else if (curChar == '\\')
					flag = true;
			} else
				flag = false;
		} //为了效率，不检查语法是否正确，如果字符串左右两边不匹配，将出现StringIndexOutOfBoundsException异常
		++pos;
		string.append(json.substring(first, pos));
		return pos;
	}

	/**
	 * 设置其它字符串，比如number,true,false,null，值将放在_string中
	 * 返回位置 指向endChar，如果字符串的结束不是因为endChar而是因为字符末尾，则返回位置指向\0
	 */
	private int getStringWithoutQuota(int pos, StringBuffer string, char... endChars) {
		int first = pos;
		boolean notEnd = true;
		while(notEnd && pos < json.length()){
			for (char endChar : endChars) {
				if (json.charAt(pos) == endChar)
					notEnd = false;
			}
			if(notEnd)
				++pos;
		}
		string.append(json.substring(first, pos));
		if(notEnd) //说明到了字符串未尾
			return json.length();
		return pos;
	}

}
