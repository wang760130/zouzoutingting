package com.zouzoutingting.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zouzoutingting.service.IFeedbackService;
import com.zouzoutingting.utils.RequestParamUtil;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年4月24日
 */
@Controller
public class FeedbackController extends BaseController {
	
	@Autowired
	private IFeedbackService feedbackService;
	
	@RequestMapping(value = "/feedback/add", method = RequestMethod.POST)
	public void add(HttpServletRequest request, HttpServletResponse response) {
		String message = RequestParamUtil.getParam(request, "message", "");
		
		if(message == null || "".equals(message)) {
			gzipCipherResult(RETURN_CODE_EXCEPTION, "数据不能为空", NULL_OBJECT, request, response);
			return ;
		}
		
		try {
			long uid = Long.valueOf(request.getAttribute("uid") + "");
			feedbackService.add(uid, message);
			gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, NULL_OBJECT, request, response);
			return ;
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, NULL_OBJECT, request, response);
			return ;
		}
			
	}
	
}
