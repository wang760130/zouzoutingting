package com.zouzoutingting.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年8月14日
 */
@Controller
public class DynamicController extends BaseController {
	
	@RequestMapping(value = "/dynamic", method = RequestMethod.POST)
	public void dynamic(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("version", 3);
		map.put("md5", "thisismd5");
		map.put("isDynamic", false);
		map.put("url", "http://zztt.oss-cn-beijing.aliyuncs.com/android/hotFix.jar");
		gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, map, request, response);
		return ;
	}
}
