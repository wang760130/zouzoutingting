package com.zouzoutingting.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 参数合法性校验类
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年5月28日
 */
public class ValidityUtil {

	private static final int PHOME_NUM_LENGTHS = 11;
	
	public static boolean checkPhoneNum(String phoneNum) {
		if(isNum(phoneNum, PHOME_NUM_LENGTHS)) {
			return true;
		}
		return false;
	}
	
	public static boolean isNum(String num, int length) {
		return (StringUtils.isNumeric(num) && num.length() == length);
	}
}
