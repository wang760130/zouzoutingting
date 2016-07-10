package com.zouzoutingting.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zouzoutingting.model.AppVersion;
import com.zouzoutingting.service.IAppVersionService;
import com.zouzoutingting.utils.RequestParamUtil;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年5月3日
 */
@Controller
public class AppVersionController extends BaseController {

	@Autowired
	private IAppVersionService appVersionService;
	
	@RequestMapping(value = "/checkappversion", method = RequestMethod.POST)
	public void checkAppVersion(HttpServletRequest request, HttpServletResponse response) {
		String os = RequestParamUtil.getParam(request, "os", "");
		int version = RequestParamUtil.getIntegerParam(request, "version", -1);
		
		try {
			if(os == null || os.equals("")) {
				gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, RETUEN_MESSAGE_PARAMETER_ERROR, NULL_OBJECT, request, response);
				return ;
			}
			
			if(version == -1) {
				gzipCipherResult(RETURN_CODE_PARAMETER_ERROR, RETUEN_MESSAGE_PARAMETER_ERROR, NULL_OBJECT, request, response);
				return ;
			}
			
			AppVersion appVersion = appVersionService.getNewAppVersionByOs(os);
			if(appVersion == null) {
				gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, NULL_OBJECT, request, response);
				return ;
			}
			
			Date publicDate = appVersion.getPublicDate();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			String publicDateStr = sdf.format(publicDate);
			appVersion.setPublicDateStr(publicDateStr);
			
			int newVersion = appVersion.getVersion();
			int minVersion = appVersion.getMinVersion();
			
			if(version < newVersion) {
				appVersion.setUpdate(true);
			} else {
				appVersion.setUpdate(false);
			}
			
			if(version < minVersion) {
				appVersion.setForceUpdate(true);
			} else {
				appVersion.setForceUpdate(false);
			}
			gzipCipherResult(RETURN_CODE_SUCCESS, RETURN_MESSAGE_SUCCESS, appVersion, request, response);
			return ;
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			gzipCipherResult(RETURN_CODE_EXCEPTION, RETURN_MESSAGE_EXCEPTION, NULL_ARRAY, request, response);
			return ;
		}
	}

}
