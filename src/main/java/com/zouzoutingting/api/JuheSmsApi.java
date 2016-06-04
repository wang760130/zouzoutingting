package com.zouzoutingting.api;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 聚合数据，短信API服务
 * https://www.juhe.cn/docs/api/id/54
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月5日
 */
public class JuheSmsApi {
	
	private static final String APP_KEY = "a2fb4acff902ada46a95231713e1570d";
	private static final String TPL_ID = "";
	private static final String SMS_SEND_URL = "http://v.juhe.cn/sms/send?mobile=%s&tpl_id=%s&tpl_value=%s&key=%s";
	
	public static boolean sendVcCode(int phone, int vcCode) {
		
		try {
			String code = "#code#"+vcCode;
			code = URLEncoder.encode(code, "utf-8");
			String url = String.format(SMS_SEND_URL, phone, TPL_ID, code, APP_KEY);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
}
