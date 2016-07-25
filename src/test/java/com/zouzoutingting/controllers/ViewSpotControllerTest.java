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
public class ViewSpotControllerTest {
	
	@Test
	public void viewspotsTest() {
		String url = Global.HOST_URL + "/cityspot";
		try {
			JSONObject result = HttpTestUtils.testUrl(url,"cityid=1&lon=108.980486&lat=34" +
					".219017&token=/v8F00YWcAfz7IAtE3/+l5/9ljv+ykrgemqmaxqcFPQ=");
			System.out.println(JSON.toJSONString(result, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void viewspotTest() {
		String url = Global.HOST_URL + "/viewspot";
		try {
			JSONObject result = HttpTestUtils.testUrl(url,"vid=12");
			System.out.println(JSON.toJSONString(result, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
