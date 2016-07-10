package com.zouzoutingting.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zouzoutingting.api.JuheSmsApi;
import com.zouzoutingting.model.User;
import com.zouzoutingting.service.IUserService;
import com.zouzoutingting.service.IVcCodeService;
import com.zouzoutingting.utils.RequestParamUtil;
import com.zouzoutingting.utils.TokenUtil;
import com.zouzoutingting.utils.ValidityUtil;
import com.zouzoutingting.utils.VcCodeUtil;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年5月28日
 */

@Controller
public class LoginController extends BaseController {
	
	// 测试帐号，输入任意验证码都能登录
	private final String[] TEST_PHONE = {"13500000000","13600000000","13700000000","13800000000", "13900000000"};
	
	@Autowired
	private IVcCodeService vcCodeService;
	
	@Autowired
	private IUserService userService;
	
	/**
	 * 发送验证码
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/vccode", method = RequestMethod.POST)
	public void vccode(HttpServletRequest request, HttpServletResponse response) {
		String phone = RequestParamUtil.getParam(request, "phone", "");
		
		try {
			if(ValidityUtil.checkPhoneNum(phone) == false) {
				gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, "手机号验证失败", NULL_OBJECT, request, response);
				return ;
			}
			
			int vcCode = VcCodeUtil.genVcCode();
			
			if(JuheSmsApi.sendVcCode(Long.valueOf(phone), vcCode)) {
				vcCodeService.addVcCode(Long.valueOf(phone), vcCode);
				gzipCipherResult(RETURN_CODE_SUCCESS, "验证码发送成功", NULL_OBJECT, request, response);
				return ;
			} else {
				gzipCipherResult(RETURN_CODE_EXCEPTION, "验证码发送失败", NULL_OBJECT, request, response);
				return ;
			}
		} catch(Exception e) {
			logger.info(e.getMessage(), e);
			gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, NULL_OBJECT, request, response);
			return ;
		}
	}
	
	/**
	 * 登录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void add(HttpServletRequest request, HttpServletResponse response) {
		String phone = RequestParamUtil.getParam(request, "phone", "");
		String vcCode = RequestParamUtil.getParam(request, "vccode", "");
		
		try {
			if(ValidityUtil.checkPhoneNum(phone) == false) {
				gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, "手机号验证失败", NULL_OBJECT, request, response);
				return ;
			}
			
			if(VcCodeUtil.checkVcCode(vcCode) == false) {
				gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, "验证失败", NULL_OBJECT, request, response);
				return ;
			}
			
			if(this.checkTestPhone(phone) == true) {
				User user = userService.getUserByPhone(Long.valueOf(phone));
				long uid = user.getId();
				String token = TokenUtil.generateToken(uid);
				request.setAttribute("token", token);
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("token", token);
				
				gzipCipherResult(RETURN_CODE_SUCCESS, "登入成功", map, request, response);
				return ;
			}
			
			if(vcCodeService.checkVcCode(Long.valueOf(phone), Integer.valueOf(vcCode))) {
				User user = userService.getUserByPhone(Long.valueOf(phone));
				long uid = -1L;
				if(user != null) {
					uid = user.getId();
				} else {
					uid = userService.createUserByPhone(Long.valueOf(phone));
				}
				
				String token = TokenUtil.generateToken(uid);
				request.setAttribute("token", token);
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("token", token);
				
				gzipCipherResult(RETURN_CODE_SUCCESS, "登入成功", map, request, response);
				return ;
			} else {
				gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, "验证失败", NULL_OBJECT, request, response);
				return ;
			}
		} catch(Exception e) {
			logger.info(e.getMessage(), e);
			gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, NULL_OBJECT, request, response);
			return ;
		}
	}
	
	private boolean checkTestPhone(String phone) {
		for(String testPhone : TEST_PHONE) {
			if(testPhone.equals(phone)) {
				return true;
			}
		}
		return false;
	}

}
