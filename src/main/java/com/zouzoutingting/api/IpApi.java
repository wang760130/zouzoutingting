package com.zouzoutingting.api;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zouzoutingting.utils.HttpUtil;

/**
 * 新浪IP API
 * {"city":"北京","country":"中国","desc":"","district":"","end":-1,"isp":"","province":"北京","ret":1,"start":-1,"type":""}
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月8日
 */
public class IpApi {
	
	private static Logger logger = Logger.getLogger(IpApi.class);
	private static final String URL = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=&s";
	
	public static JSONObject ipLockUp(String ip) {
		JSONObject jsonObject = null;
		
		try {
			String url = String.format(URL, ip);
			String httpResult = HttpUtil.get(url);
			
			jsonObject = JSON.parseObject(httpResult);
		} catch (IOException e) {
			e.printStackTrace();
			logger.info(e.getMessage(), e);
		}
		return jsonObject;
	}
	
	public static void main(String[] args) {
		JSONObject jsonObject = IpApi.ipLockUp("123.125.250.164");
		System.out.println(jsonObject);
		
		System.out.println(jsonObject.getString("country") + " --- " + jsonObject.getString("province") + " --- " + jsonObject.getString("city"));
	}
}
