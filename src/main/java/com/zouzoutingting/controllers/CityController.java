package com.zouzoutingting.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.zouzoutingting.model.City;
import com.zouzoutingting.service.ICityService;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年3月27日
 */
@Controller
public class CityController extends BaseController {
	
	@Autowired
	private ICityService cityService;
	 
	@RequestMapping(value = "/citys", method = RequestMethod.GET)
	public ModelAndView list() {
		System.out.println("citys");
		List<City> cityList = cityService.getAll();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", cityList);
		return new ModelAndView(new MappingJackson2JsonView(),map);
	}

}
