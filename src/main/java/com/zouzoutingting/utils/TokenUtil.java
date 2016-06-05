package com.zouzoutingting.utils;

import com.zouzoutingting.common.Global;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月1日
 */
public class TokenUtil {
	
	/**
	 * 生成用户登录令牌，长度为32位
	 * @return
	 */
	public static String generateToken(long uid)  {
		return MD5.Md5Encryt(MD5.Md5Encryt(Long.toString(uid)) + MD5.Md5Encryt(Global.DESKEY));
	}
	
	public static boolean checkToken(long uid, String token) {
		if(token == null) {
			return false;
		}
		
		if(TokenUtil.generateToken(uid).equals(token)) {
			return true;
		}
		return false;
	}
}	
