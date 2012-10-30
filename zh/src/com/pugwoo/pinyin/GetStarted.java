package com.pugwoo.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;

public class GetStarted {

	public static void main(String[] args) {
		{
			String[] pinyinArray = PinyinHelper
					.toTongyongPinyinStringArray('李');
			// String[] pinyinArray = PinyinHelper.toYalePinyinStringArray('李');
			for (int i = 0; i < pinyinArray.length; i++) {
				System.out.println(pinyinArray[i]);
			}
		}
	}

}
