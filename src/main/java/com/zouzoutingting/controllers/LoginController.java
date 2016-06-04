package com.zouzoutingting.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zouzoutingting.api.JuheSmsApi;
import com.zouzoutingting.utils.RequestParamUtil;
import com.zouzoutingting.utils.ValidityUtil;
import com.zouzoutingting.utils.VcCodeUtil;

/**
 * 
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年5月28日
 */

@Controller
public class LoginController extends BaseController {
	
	/**
	 * 发送验证码
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/vccode", method = RequestMethod.POST)
	public void vccode(HttpServletRequest request, HttpServletResponse response) {
		String phone = RequestParamUtil.getParam(request, "phone", "");
		boolean checkResult = ValidityUtil.checkPhoneNum(phone);
		
		if(checkResult == false) {
			gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, RETUEN_MESSAGE_PARAMETER_ERROR, NULL_ARRAY, request, response);
		}
		
		int vcCode = VcCodeUtil.genVcCode();
		boolean sendSuccess = JuheSmsApi.sendVcCode(Integer.valueOf(vcCode), vcCode);
		
		gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, NULL_ARRAY, request, response);
	}
	
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void add(HttpServletRequest request, HttpServletResponse response) {
	
	}

}
