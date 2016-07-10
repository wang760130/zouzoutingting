package com.zouzoutingting.controllers;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zouzoutingting.common.Global;
import com.zouzoutingting.utils.HttpTestUtils;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年7月10日
 */
public class UserControllerTest {
	
	@Test
	public void userTest() {
		String url = Global.HOST_URL + "/user";
		try {
			JSONObject result = HttpTestUtils.testUrl(url,"token=/v8F00YWcAeOMI4hgvYWBDOHxD/WXzuEJeWTyAABYiU=");
			System.out.println(JSON.toJSONString(result, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
