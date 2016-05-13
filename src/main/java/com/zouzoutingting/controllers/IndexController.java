package com.zouzoutingting.controllers;

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
	
	@RequestMapping(value={"", "/"}, method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model, HttpServletRequest request, HttpServletResponse response) {
		model.setViewName("forward:index.jsp");
	    return model;
	}
}
