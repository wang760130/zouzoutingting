package com.zouzoutingting.controllers;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zouzoutingting.common.Global;
import com.zouzoutingting.utils.HttpTestUtils;

public class LoginControllerTest {

	@Test
	public void vccodeTest() {
		String url = Global.HOST_URL + "/vccode";
		try {
			JSONObject result = HttpTestUtils.testUrl(url, "phone=15210740626");
			System.out.println(JSON.toJSONString(result, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void loginTest() {
		String url = Global.HOST_URL + "/login";
		try {
			JSONObject result = HttpTestUtils.testUrl(url, "phone=15210740626&vccode=704682");
			System.out.println(JSON.toJSONString(result, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
