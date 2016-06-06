package com.zouzoutingting.controllers;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zouzoutingting.common.Global;
import com.zouzoutingting.utils.HttpTestUtils;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年5月3日
 */
public class AppVersionControllerTest {

	@Test
	public void checkAppVersionTest() {
		String url = Global.HOST_URL + "/checkappversion";
		try {
			JSONObject result = HttpTestUtils.testUrl(url, "os=ios&version=1");
			System.out.println(JSON.toJSONString(result, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
