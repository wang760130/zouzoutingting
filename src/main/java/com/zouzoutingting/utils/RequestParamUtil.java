package com.zouzoutingting.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.zouzoutingting.common.Global;


/**
 * APP请求接口统一封装 所有参数通过post或get请求传递
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年4月4日
 */
public class RequestParamUtil {

	private static Logger logger = Logger.getLogger(RequestParamUtil.class);

	public static final String REQUEST_PARAM = "param";
	public static final String REQUEST_PARAM_MAP = "param_map";

	/**
	 * 请求参数加密传输 APP端调用
	 * @param param 参数
	 * @return
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	public static String encryptParan(String param) throws Exception {
		byte[] encryptbytes = DESCipher.encrypt(param.getBytes("utf-8"),Global.RESPONSE_DESKEY);
		String byte2str = Base64.encode(encryptbytes);
		String strenpar = REQUEST_PARAM + "=" + URLEncoder.encode(byte2str, "utf-8");
		return strenpar;
	}
	
	/**
	 * 参数解密
	 * @param request
	 */
	public static void decryptParam(HttpServletRequest request) {
		String param = request.getParameter(REQUEST_PARAM);
		
		Map<String, String> paramMap = new HashMap<String, String>();
		try {
			if(StringUtils.isNotBlank(param)){
				byte[] strParBytes = Base64.decode(param);
				String params = new String(DESCipher.decrypt(strParBytes,Global.RESPONSE_DESKEY), "utf-8");
				for (String p : params.split("\\&")) {
					String[] paramsArr = p.split("\\=");
					if(paramsArr !=null && paramsArr.length>=2){
						String k = paramsArr[0];
						String v = URLDecoder.decode(paramsArr[1],"utf-8");
						paramMap.put(k, v);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		request.setAttribute(REQUEST_PARAM_MAP, paramMap);
	}
	
	/**
	 * 获取参数
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getParamMap(HttpServletRequest request) {
		return (Map<String, String>) request.getAttribute(REQUEST_PARAM_MAP);
	}
	
	/**
	 * 获取参数
	 * @param request
	 */
	public static String getParam(HttpServletRequest request, String name, String defaultVal) {
		@SuppressWarnings("unchecked")
		Map<String, String> paramMap = (Map<String, String>) request.getAttribute(REQUEST_PARAM_MAP);
		if (paramMap.containsKey(name)) {
			return paramMap.get(name);
		}
		return defaultVal;
	}

	public static Integer getIntegerParam(HttpServletRequest request, String name, Integer defaultVal) {
		Integer retInteger = defaultVal;
		String value = request.getParameter(name);
		if(value!=null){
			if(StringUtils.isNumeric(value)){
				retInteger = Integer.parseInt(value);
			}
		}
		return retInteger;
	}

	public static Long getLongParam(HttpServletRequest request, String name, Long defaultVal) {
		Long retLong = defaultVal;
		String value = request.getParameter(name);
		if(value!=null){
			if(StringUtils.isNumeric(value)){
				retLong = Long.parseLong(value);
			}
		}
		return retLong;
	}
	
}
