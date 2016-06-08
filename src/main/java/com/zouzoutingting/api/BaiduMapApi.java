package com.zouzoutingting.api;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zouzoutingting.utils.HttpUtil;

/**
 * 百度地图API
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月8日
 */

/**
 * {
	"status": 0,
	"result": {
		"location": {
			"lng": 108.98048599999994,
			"lat": 34.21901703643193
		},
		"formatted_address": "陕西省西安市雁塔区芙蓉桥",
		"business": "大雁塔,西影路,南郊大学区",
		"addressComponent": {
			"adcode": "610113",
			"city": "西安市",
			"country": "中国",
			"direction": "",
			"distance": "",
			"district": "雁塔区",
			"province": "陕西省",
			"street": "芙蓉桥",
			"street_number": "",
			"country_code": 0
		},
		"pois": [],
		"poiRegions": [],
		"sematic_description": "大唐芙蓉园北76米",
		"cityCode": 233
	}
}
**/
public class BaiduMapApi {

	private static Logger logger = Logger.getLogger(BaiduMapApi.class);
	
	private static final String AK = "472CqyURtZNAs2gQFI8vDFCnRyw2N6SZ";
	
	private static final String GEOCODE_URL = "http://api.map.baidu.com/geocoder/v2/?ak=%s&location=%f,%f&output=json&pois=0";;
	
	public static JSONObject geocoder(double lon, double lat) {
		JSONObject jsonObject = null;
		try {
			String url = String.format(GEOCODE_URL, AK, lat, lon);
			String httpResult = HttpUtil.get(url);
			
			jsonObject = JSON.parseObject(httpResult);
		} catch (IOException e) {
			e.printStackTrace();
			logger.info(e.getMessage(), e);
		}
		return jsonObject;
	}
	
	public static void main(String[] args) throws ParseException, IOException {
		JSONObject jsonObject = BaiduMapApi.geocoder(108.980486, 34.219017);
		System.out.println(jsonObject.getJSONObject("result").getJSONObject("addressComponent").getString("city"));
	}
}
