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
	 * 具体生成公式为：Token = md5(md5(userid)+time(long)+md5(randomStr+finalKey))
	 * @return
	 */
	public static String genToken(long uid)  {
		return MD5.Md5Encryt(MD5.Md5Encryt(Long.toString(uid)) + System.currentTimeMillis() + MD5.Md5Encryt(Math.random() + Global.DESKEY));
	}
	
}	
