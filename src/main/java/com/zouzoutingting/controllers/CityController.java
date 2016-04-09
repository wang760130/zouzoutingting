package com.zouzoutingting.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	@RequestMapping(value = "/city", method = RequestMethod.POST)
	public void city(HttpServletRequest request, HttpServletResponse response) {
		String cityid = RequestParamUtil.getParam(request, "id", "1");
		try {
			City city = cityService.load(Integer.valueOf(cityid));
			gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, city, request, response);
		} catch (Exception e) {
			gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, null, request, response);
		}
	}
	
	@RequestMapping(value = "/citys", method = RequestMethod.POST)
	public void citys(HttpServletRequest request, HttpServletResponse response) {
		List<City> cityList = cityService.getAll();
		try {
			gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, cityList, request, response);
		} catch (Exception e) {
			gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, null, request, response);
		}
	}
}
