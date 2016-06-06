package com.zouzoutingting.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zouzoutingting.common.Global;
import com.zouzoutingting.utils.IpUtil;
import com.zouzoutingting.utils.RequestParamUtil;
import com.zouzoutingting.utils.ThreadLocalRandom;
import com.zouzoutingting.utils.TokenUtil;

/**
 * 解密参数  && 打印访问日志
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年4月4日
 */
public class AccessLoggerInterceptor implements HandlerInterceptor {

	private static Logger logger = Logger.getLogger(AccessLoggerInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		/**
		 * 参数解密
		 */
		RequestParamUtil.decryptParam(request);
		
		/**
		 * 打印访问日志相关
		 */
		long timeMillis = System.currentTimeMillis();
		long random = ThreadLocalRandom.current().nextLong(100l,999l);
		String requestid = String.valueOf(timeMillis) + String.valueOf(random);
		Map<String, String> paramMap = RequestParamUtil.getParamMap(request);
		
		String token = paramMap.get("token");
		long uid = TokenUtil.getUid(token);
		
		StringBuffer sb = new StringBuffer();

		sb.append("requestid=").append(requestid).append(",");
		sb.append("token=").append(token).append(",");
		sb.append("uid=").append(uid).append(",");
		
		sb.append("url=");
		sb.append(request.getRequestURL());
		String queryString = request.getQueryString();
		if(queryString != null && !"".equals(queryString)) {
			try {
				queryString = URLDecoder.decode(queryString,"utf-8");
			} catch (UnsupportedEncodingException e) {} 
			sb.append("?");
			sb.append(queryString);
		}
		sb.append(",");
		
		if(paramMap != null && paramMap.size() > 0) {
			sb.append("param=");
			for (String key : paramMap.keySet()) { 
				sb.append(key);
				sb.append(":");
				sb.append(paramMap.get(key));
				sb.append(",");
			}
		}
		
		sb.append("ip=").append(IpUtil.getClickUserIp(request));
		
		logger.info(sb.toString());
		
		request.setAttribute("token", token);
		request.setAttribute("uid", uid);
		request.setAttribute("requestid", requestid);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		String content = (String)request.getAttribute(Global.RESULT_CONTENT);
		Object requestid = request.getAttribute("requestid");
		if(requestid != null) {
			String requestId = requestid.toString();
			long currentTime = System.currentTimeMillis();
			long timeMillis = Long.valueOf(requestId.substring(0, requestId.length() - 3));
			long executeTime = currentTime - timeMillis;
			StringBuffer sb = new StringBuffer();
			sb.append("requestid=").append(requestId).append(",");
			sb.append("executeTime=").append(executeTime).append("ms");
			if(content != null && !"".equals(content)) {
				sb.append(",result=").append(content);
			}
			logger.info(sb.toString());
		}
	}

	
}
