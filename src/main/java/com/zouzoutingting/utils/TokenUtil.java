package com.zouzoutingting.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.zouzoutingting.common.Global;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月1日
 */
public class TokenUtil {
	
	/**
	 * 生成用户登录令牌
	 * @return
	 * @throws Exception 
	 */
	public static String generateToken(long uid) throws Exception  {
		String str = "UID=" + uid + "&TS=" + System.currentTimeMillis();
		byte[] bytes = DES.encrypt(String.valueOf(str).getBytes(Global.DEFUALT_CHARSET),Global.DESKEY);
		return new String(Base64.encodeBase64(bytes));
	}
	
	public static long getUid(String token) throws Exception {
		long uid = -1L;
		if(token == null || token.equals("")) {
			return uid;
		}
		byte[] bytes = Base64.decodeBase64(token);
		String str = new String(DES.decrypt(bytes,Global.DESKEY), Global.DEFUALT_CHARSET);
		
		Matcher matcher = Pattern.compile("UID=[\\d]+").matcher(str);
		if(matcher.find()) {
			uid = getLong(matcher.group().substring(4), -1L);
		}
		
		return uid;
	}
	
	public static long getTs(String token) throws Exception {
		long ts = -1L;
		if(token == null || token.equals("")) {
			return ts;
		}
		byte[] bytes = Base64.decodeBase64(token);
		String str = new String(DES.decrypt(bytes,Global.DESKEY), Global.DEFUALT_CHARSET);
		
		Matcher matcher = Pattern.compile("TS=[\\d]+").matcher(str);
		if(matcher.find()) {
			ts = getLong(matcher.group().substring(3), -1L);
		}
		
		return ts;
	}
	
	private static final long getLong(Object obj, long defaultValue) {
		try {
			if ((obj == null) || (StringUtils.isEmpty(obj.toString().trim())))
				return defaultValue;
			return Long.valueOf(obj.toString()).longValue();
		} catch (Exception e) {
		}
		return defaultValue;
	}
	
	
	public static void main(String[] args) throws Exception {
		long uid = 123456L;
		String token = TokenUtil.generateToken(uid);
		
		System.out.println("token = " + token);
		System.out.println(TokenUtil.getUid(token));
		System.out.println(TokenUtil.getTs(token));
	}
	
}	
