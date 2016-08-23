package com.zouzoutingting.components.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.ParseException;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zouzoutingting.utils.HttpUtil;

/**
 * 聚合数据，短信API服务
 * https://www.juhe.cn/docs/api/id/54
 * 备注：同1个号码同1个签名的内容30秒内只能发1条，1分钟内只能发2条，30分钟内只能发3条
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月5日
 */
public class JuheSmsApi {
	private static Logger logger = Logger.getLogger(JuheSmsApi.class);

	private static final String APP_KEY = "a2fb4acff902ada46a95231713e1570d";
	private static final String TPL_ID = "16159";
	private static final String SMS_SEND_URL = "http://v.juhe.cn/sms/send?mobile=%s&tpl_id=%s&tpl_value=%s&key=%s";
	
	public static boolean sendVcCode(long phone, int vcCode) {
		boolean result = false;
		try {
			String value = "#app#=走走听听旅行&#code#="+vcCode;
			value = URLEncoder.encode(value, "utf-8");
			String url = String.format(SMS_SEND_URL, phone, TPL_ID, value, APP_KEY);
			String httpResult = HttpUtil.get(url);
			logger.info("sendVcCode url :" + url + ", result :" + httpResult);
			JSONObject json = JSON.parseObject(httpResult);
			if(json != null) {
				Integer errorCode = json.getInteger("error_code");
				if(errorCode == 0) {
					result =  true;
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		return result;
	}
	
}
