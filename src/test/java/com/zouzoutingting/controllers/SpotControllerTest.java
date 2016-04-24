package com.zouzoutingting.controllers;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zouzoutingting.utils.HttpTestUtils;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年4月24日
 */
public class SpotControllerTest {

//	private final static String HOST_URL = "http://api.imonl.com";
	private final static String HOST_URL = "http://123.56.242.84:8080";
	
	@Test
	public void spotsTest() {
		String url = HOST_URL + "/spots";
		try {
			JSONObject result = HttpTestUtils.testUrl(url,"vid=13");
			System.out.println(JSON.toJSONString(result, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
