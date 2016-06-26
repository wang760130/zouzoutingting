package com.zouzoutingting.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.zouzoutingting.api.BaiduMapApi;
import com.zouzoutingting.model.City;
import com.zouzoutingting.service.ICityService;
import com.zouzoutingting.utils.RequestParamUtil;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年3月27日
 */
@Controller
public class CityController extends BaseController {
	
	@Autowired
	private ICityService cityService;
	
	/**
	 * 城市列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/citys", method = RequestMethod.POST)
	public void citys(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			double lon = RequestParamUtil.getDoubleParam(request, "lon", 0.0d);
			double lat = RequestParamUtil.getDoubleParam(request, "lat", 0.0d);
			
			List<City> list = cityService.getAll();
			
			String currentCityName = "";
			long currentCityId = 0L;
			boolean isInCitys = false;
			if(list != null && list.size() > 0) {
				if(lon != 0.00d && lat != 0.00d) {
					JSONObject jsonObject = BaiduMapApi.geocoder(lon, lat);
					if(jsonObject != null) {
						Integer cityCode = jsonObject.getJSONObject("result").getInteger("cityCode");
						if(cityCode != null) {
							City city = cityService.getCityByCode(cityCode);
							if(city != null) {
								isInCitys = true;
								currentCityName = city.getName();
								currentCityId = city.getId();
							}
						}
					}
				}
				resultMap.put("isInCitys", isInCitys);
				if(isInCitys == true) {
					resultMap.put("currentCityName", currentCityName);
					resultMap.put("currentCityId", currentCityId);
				}
				resultMap.put("citys", list);
				
				gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, resultMap, request, response);
			} else {
				gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_NULL, NULL_ARRAY, request, response);
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, NULL_ARRAY, request, response);
		}
	}
	
	/**
	 * 城市详情
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/city", method = RequestMethod.POST)
	public void city(HttpServletRequest request, HttpServletResponse response) {
		int cityid = RequestParamUtil.getIntegerParam(request, "id", 1);
		try {
			City city = cityService.load(cityid);
			if(city != null) {
				gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, city, request, response);
			} else {
				gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_NULL, NULL_OBJECT, request, response);
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, NULL_OBJECT, request, response);
		}
	}
	
	
}
