package com.zouzoutingting.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zouzoutingting.utils.ExplorerUtil;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年7月10日
 */
@Controller
public class MobileController extends BaseController {
	private static final String WEXIN_URL = "mobile.jsp";
	private static final String ANDROID_URL = "http://oss.imonl.com/android/beta4.apk";
	private static final String IOS_URL = "https://itunes.apple.com/app/id1132634339";
	private static final String QRCODE_URL = "http://www.imonl.com/qrcode";
	
	@RequestMapping(value = "/mobile", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model, HttpServletRequest request, HttpServletResponse response) {
		
		if(ExplorerUtil.isWeixinExporer(request)) {
			model.setViewName("forward:" + WEXIN_URL);
		} else {
			if(ExplorerUtil.isMobile(request)) {
				
				if(ExplorerUtil.isAndroid(request)) {
					// Android直接下载
					model.setViewName("redirect:" + ANDROID_URL);
				} else if(ExplorerUtil.isIos(request)) {
					// IOS跳苹果应用市场
					 model.setViewName("redirect:" + IOS_URL);
				} else {
					// 其他跳到二维码页面
				    model.setViewName("redirect:" + QRCODE_URL);
				}
	 			
			} else {
				// 不在手机中，跳到二维码页面
				model.setViewName("redirect:" + QRCODE_URL);
			}
		}
		
	    return model;
	}

}
