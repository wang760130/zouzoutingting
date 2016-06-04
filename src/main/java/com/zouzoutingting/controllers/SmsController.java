package com.zouzoutingting.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zouzoutingting.utils.RequestParamUtil;
import com.zouzoutingting.utils.ValidityUtil;

/**
 * 发送短信统一接口
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年5月28日
 */
@Controller
public class SmsController extends BaseController {
	
	/**
	 * 发送注册&登入验证码
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/sns/vccode", method = RequestMethod.POST)
	public void vccode(HttpServletRequest request, HttpServletResponse response) {
		String phoneNum = RequestParamUtil.getParam(request, "phonenum", "");
		boolean result = ValidityUtil.checkPhoneNum(phoneNum);
		if(result == false) {
			gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, "RETUEN_MESSAGE_PARAMETER_ERROR", NULL_ARRAY, request, response);
		}
		gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, NULL_ARRAY, request, response);
	}
	
}
