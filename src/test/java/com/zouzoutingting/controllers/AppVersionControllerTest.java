package com.zouzoutingting.controllers;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zouzoutingting.utils.HttpTestUtils;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年5月3日
 */
public class AppVersionControllerTest {

//	private final static String HOST_URL = "http://api.imonl.com";
	private final static String HOST_URL = "http://123.56.242.84:8080";
	
	@Test
	public void checkAppVersionTest() {
		String url = HOST_URL + "/checkappversion";
		try {
			JSONObject result = HttpTestUtils.testUrl(url, "os=ios&version=1");
			System.out.println(JSON.toJSONString(result, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
