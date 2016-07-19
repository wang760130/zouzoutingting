package com.zouzoutingting.utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年7月15日
 */
public class ParamUtil {
	
	private static Logger logger = Logger.getLogger(ParamUtil.class);
	
	private static String getString(HttpServletRequest request, String s) {
		String temp = null;
		try {
			temp = (request.getParameter(s) == null) ? null :request.getParameter(s).trim();
			temp = DropHtmlTags(temp);
		} catch (Exception e) {
			logger.error("ParamUtil getString error,s:" + s + "temp:" + temp, e);
		}
		return temp;
	}

	public static String getString(HttpServletRequest request, String s,
			String defaultString) {
		String s1 = getString(request, s);
		if (StringUtils.isBlank(s1))
			return defaultString;
		return s1;
	}

	public static int getInt(HttpServletRequest request, String s,
			int defaultInt) {
		String temp = null;
		try {
			temp = getString(request, s);
			if (StringUtils.isBlank(temp)) {
				return defaultInt;
			}
			return Integer.parseInt(temp);
		} catch (NumberFormatException e) {
			logger.error("ParamUtil getInt error,s:" + s + "temp:" + temp, e);
		}
		return 0;
	}

	public static long getLong(HttpServletRequest request, String s,
			long defaultLong) {
		String temp = null;
		try {
			temp = getString(request, s);
			if (StringUtils.isBlank(temp)) {
				return defaultLong;
			}
			return Long.parseLong(temp);
		} catch (NumberFormatException e) {
			logger.error("ParamUtil getLong error,s:" + s + "temp:" + temp, e);
		}
		return 0L;
	}

	public static short getShort(HttpServletRequest request, String s,
			short defaultShort) {
		String temp = null;
		try {
			temp = getString(request, s);
			if (StringUtils.isBlank(temp)) {
				return defaultShort;
			}
			return Short.parseShort(temp);
		} catch (NumberFormatException e) {
			logger.error("ParamUtil getShort error,s:" + s + "temp:" + temp, e);
		}
		return 0;
	}
	
	public static float getFloat(HttpServletRequest request, String s,
			float defaultFloat) {
		String temp = null;
		try {
			temp = getString(request, s);
			if (StringUtils.isBlank(temp)) {
				return defaultFloat;
			}
			return Float.parseFloat(temp);
		} catch (NumberFormatException e) {
			logger.error("ParamUtil getFloat error,s:" + s + "temp:" + temp, e);
		}
		return 0f;
	}
	
	public static BigDecimal getBigDecimal(HttpServletRequest request, String s) {
		String value = getString(request, s, "");
		if (StringUtils.isNotBlank(value)) {
			return new BigDecimal(value);
		}
		return null;
	}

	public static String getHeader(HttpServletRequest request, String s,
			String defaultString) {
		String s1 = request.getHeader(s);
		if (s1 == null)
			return defaultString;
		return s1;
	}

	private static String DropHtmlTags(String HtmlText) {
		if (StringUtils.isEmpty(HtmlText)) {
			return "";
		}
		HtmlText = HtmlText.replaceAll("\"", "“").replaceAll("'", "＇")
				.replaceAll("\\\\", "＼");
		HtmlText = HtmlText.replaceAll("<", "＜").replaceAll(">", "＞");
		String regxpForHtml = "<([^>]*)>";
		Pattern pattern = Pattern.compile(regxpForHtml);
		Matcher matcher = pattern.matcher(HtmlText);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
}
