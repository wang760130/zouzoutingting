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
public class FeedbackControllerTest {

	
	@Test
	public void addTest() {
		String message = "挺好的";
		String url = Global.HOST_URL + "/feedback/add";
		try {																						
			JSONObject result = HttpTestUtils.testUrl(url,"message="+ message + "&token=7VsmL13CpPP1QKZi3cd/UZDPvSfGXYJUusKHogoPC3E=");
			System.out.println(JSON.toJSONString(result, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
