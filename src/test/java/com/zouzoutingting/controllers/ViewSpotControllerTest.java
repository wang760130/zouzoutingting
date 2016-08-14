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
		String url = Global.TEST_HOST_URL + "/cityspot";
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
			JSONObject result = HttpTestUtils.testUrl(url,"/viewspot?imei=69131E02-A517-4717-A17A-AB085926408D&os=ios&token=%2Fv8F00YWcAeTjVHT1Jrp2Ygk3TIEqg4oqgLhkhf9WqA%3D&version=1.1.1&vid=26");
			System.out.println(JSON.toJSONString(result, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
