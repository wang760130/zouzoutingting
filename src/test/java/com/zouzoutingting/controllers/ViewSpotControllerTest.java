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
public class ViewSpotControllerTest {
	
	private final static String HOST_URL = "http://api.imonl.com";
//	private final static String HOST_URL = "http://123.56.242.84:8080";
	
	@Test
	public void viewspotsTest() {
		String url = HOST_URL + "/viewspots";
		try {
			JSONObject result = HttpTestUtils.testUrl(url);
			System.out.println(JSON.toJSONString(result, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void viewspotTest() {
		String url = HOST_URL + "/viewspot";
		try {
			JSONObject result = HttpTestUtils.testUrl(url,"vid=12");
			System.out.println(JSON.toJSONString(result, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
