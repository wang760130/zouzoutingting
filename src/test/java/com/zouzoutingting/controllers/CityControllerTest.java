package com.zouzoutingting.controllers;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zouzoutingting.common.Global;
import com.zouzoutingting.utils.HttpTestUtils;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年4月4日
 */
public class CityControllerTest {
	
	/** 有参数连接**/
	@Test
	public void cityTest() {
		String url = Global.HOST_URL + "/city";
		try {
			JSONObject result = HttpTestUtils.testUrl(url, "id=1");
			System.out.println(JSON.toJSONString(result, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 无参数连接**/
	@Test
	public void citysTest() {
		String url = Global.HOST_URL + "/citys";
		try {
			JSONObject result = HttpTestUtils.testUrl(url);
			System.out.println(JSON.toJSONString(result, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void cityspotTest() {
		String url = Global.HOST_URL + "/cityspot";
		try {
			JSONObject result = HttpTestUtils.testUrl(url);
			System.out.println(JSON.toJSONString(result, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void batchCityTest() {
		
		String url = Global.HOST_URL + "/citys";
		try {
			for(int i = 0; i < 100; i++) {
				JSONObject result = HttpTestUtils.testUrl(url);
				System.out.println(JSON.toJSONString(result, true));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
