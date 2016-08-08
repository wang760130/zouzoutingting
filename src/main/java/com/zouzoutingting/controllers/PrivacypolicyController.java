package com.zouzoutingting.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年7月11日
 */
@Controller
public class PrivacypolicyController extends BaseController {
	
	@RequestMapping(value = "/privacypolicy", method = RequestMethod.GET)
	public ModelAndView privacypolicy(ModelAndView model, HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("privacypolicy");
	}
}