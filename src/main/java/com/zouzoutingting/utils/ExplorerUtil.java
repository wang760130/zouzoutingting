package com.zouzoutingting.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年7月10日
 */
public class ExplorerUtil {
	
	public static boolean isWeixinExporer(HttpServletRequest request) {
		String ua = request.getHeader("USER-AGENT");
		if (ua != null && !"".equals(ua)) {
			if(ua.toLowerCase().indexOf("micromessenger") > 0) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isCrawler(HttpServletRequest request) {
		boolean result = false;
		String ua = request.getHeader("USER-AGENT");
		if (ua != null && !"".equals(ua)) {
			if (ua.toLowerCase().startsWith("curl")) {
				result = true;
			}
			if (ua.toLowerCase().startsWith("wget")) {
				result = true;
			}
		}
		return result;
	}
	
	public static boolean isMobile(HttpServletRequest request) {
		String ua = request.getHeader("USER-AGENT");
		if (ua == null || "".equals(ua)) {
			return false;
		}
		
		//排除  Windows 桌面系统   苹果桌面系统  
		String[] except = {"Windows NT", "compatible; MSIE 9.0;", "Macintosh"};
		for(String item : except) {
			if(ua.contains(item)) {
				return false;
			}
		}
		
		
		String[] keywords = { "Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser" };  
		for (String item : keywords) {  
            if (ua.contains(item)) {  
            	return true;
            }  
        } 
		
		return false;
	}
	
	public static boolean isIos(HttpServletRequest request) {
		String ua = request.getHeader("USER-AGENT");
		if (ua == null || "".equals(ua)) {
			return false;
		}
		
		String[] keywords = {"iPhone", "iPod", "iPad" };
		for (String item : keywords) {  
            if (ua.contains(item)) {  
            	return true;
            }  
        } 
		
		return false;
	}
	
	public static boolean isAndroid(HttpServletRequest request) {
		String ua = request.getHeader("USER-AGENT");
		if (ua == null || "".equals(ua)) {
			return false;
		}
		
		String keyword = "Android";  
		if(ua.contains(keyword)) {
			return true;
		}
		return false;
	}
}
