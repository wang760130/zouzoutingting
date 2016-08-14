package com.zouzoutingting.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年5月13日
 */

@Controller
public class IndexController extends BaseController {
	
	// SEO 关键字
	private static final String description = "“走走听听”为游客提供移动博物馆讲解、精彩历史、人文、民俗故事语音助手服务 “走走听听”APP核心功能有: 标准化、个性化博物馆";
	private static final String[] keywords = {"走走听听","走走听听旅行","西安走走听听","景点讲解","景点讲解APP","西安景点讲解","兵马俑","西安兵马俑","华清池","西安华清池","城墙","西安城墙","袁家村","西安袁家村","大雁塔","西安大雁塔","陕西历史博物馆"};
	
	@RequestMapping(value={"", "/"}, method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String keywordStr = "";
		for(String keyword : keywords) {
			keywordStr += keyword;
			keywordStr += ",";
		}
		if(keywordStr != null && keywordStr.endsWith(",")) {
			keywordStr = keywordStr.substring(0, keywordStr.length() - 1);
		}
		
		map.put("description", description);
		map.put("keywords", keywordStr);
	    return new ModelAndView("index", map);
	}
	
	@RequestMapping(value={"barrager", "barrager/"}, method = RequestMethod.GET)
	public void data(ModelAndView model, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		for(String keyword : keywords) {
			map = new HashMap<String, Object>();
			map.put("info", keyword);
			map.put("img", "images/ico.24x24.png");
			map.put("href", "http://www.zouzoutingting.net");
			map.put("close", false);
			map.put("speed", 1);
			map.put("color", "#fff");
			map.put("old_ie_color", "#000000");
			list.add(map);
		}
		
	    jsonResult(true, "成功", list, response);
	    return;
	}
}
