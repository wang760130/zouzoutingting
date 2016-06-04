package com.zouzoutingting.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年5月28日
 */

@Controller
public class LoginController extends BaseController {

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void add(HttpServletRequest request, HttpServletResponse response) {
	
	}

}
