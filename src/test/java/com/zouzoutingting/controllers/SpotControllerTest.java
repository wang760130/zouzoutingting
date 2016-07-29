package com.zouzoutingting.controllers;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zouzoutingting.common.Global;
import com.zouzoutingting.utils.HttpTestUtils;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年4月24日
 */
public class SpotControllerTest {

	@Test
	public void spotsTest() {
		String url = Global.TEST_HOST_URL + "/spots";
		try {
			JSONObject result = HttpTestUtils.testUrl(url,"vid=12");
			System.out.println(JSON.toJSONString(result, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
