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

import com.zouzoutingting.model.City;
import com.zouzoutingting.model.ViewSpot;
import com.zouzoutingting.service.ICityService;
import com.zouzoutingting.service.IViewSpotService;
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
	
	@Autowired
	private IViewSpotService viewSpotService;
	
	/**
	 * 城市列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/citys", method = RequestMethod.POST)
	public void citys(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<City> list = cityService.getAll();
			if(list != null && list.size() > 0) {
				gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, list, request, response);
			} else {
				gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_NULL, null, request, response);
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, null, request, response);
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
				gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_NULL, city, request, response);
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, null, request, response);
		}
	}
	
	
	/**
	 * 城市详情 + 景点列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/cityspot", method = RequestMethod.POST)
	public void citySpot(HttpServletRequest request, HttpServletResponse response) {
		int cityid = RequestParamUtil.getIntegerParam(request, "id", 1);
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			City city = cityService.load(cityid);
			List<ViewSpot> viewSpotList = viewSpotService.getViewSpotByCity(cityid);
			map.put("id", city.getId());
			map.put("name", city.getName());
			map.put("ename",city.getEname());
			map.put("pic", city.getPic());
			map.put("synopsis", city.getSynopsis());
			map.put("viewspot", viewSpotList);
			gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, map, request, response);
		} catch (Exception e) {
			gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, null, request, response);
		}
	}
}
