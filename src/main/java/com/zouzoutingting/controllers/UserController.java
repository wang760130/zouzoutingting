package com.zouzoutingting.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zouzoutingting.model.User;
import com.zouzoutingting.service.IUserService;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年7月10日
 */

@Controller
public class UserController extends BaseController {
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public void user(HttpServletRequest request, HttpServletResponse response) {
		
		long uid = Long.valueOf(request.getAttribute("uid") + "");
		if(uid < 0) {
			gzipCipherResult(RETURN_CODE_TOKEN_ERROR, RETURN_MESSAGE_TOKEN_ERROR, NULL_OBJECT, request, response);
			return ;
		}
		
		try {
			User user = userService.getUserById(uid);
			gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, user, request, response);
			return ;
		} catch(Exception e) {
			logger.info(e.getMessage(), e);
			gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, NULL_OBJECT, request, response);
			return ;
		}
		
	}
	
}
