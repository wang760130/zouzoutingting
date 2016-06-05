package com.zouzoutingting.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jerry Wang
 * @Email  jerry002@126.com
 * @date   2016年6月5日
 */
public class CookieUtil {
	
	private final static int SESSION_EXPIRY = -1;
	
	private final static String DOMAIN = "imonl.com";
	
	public void addCookie(HttpServletResponse response, String cookieName, String cookieValue, String domain, int expiry) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setPath("/");
		cookie.setDomain(domain);
		cookie.setMaxAge(expiry);
		response.addCookie(cookie);
	}
	
	public void addCookie(HttpServletResponse response, String cookieName, String cookieValue, int expiry) {
		this.addCookie(response, cookieName, cookieValue, DOMAIN, expiry);
	}
	
	public void addTicketCookie(HttpServletResponse response, String ticket) {
		this.addTicketCookie(response, ticket, SESSION_EXPIRY);
	}
	
	public void addTicketCookie(HttpServletResponse response, String ticket, int expiry) {
		this.addCookie(response, "ticket", ticket, DOMAIN, expiry);
	}
	
	public void deleteTicketCookie(HttpServletResponse response) {
		addCookie(response, "ticket", "", DOMAIN, 0);
	}
	
	public Cookie getCookie(String cookieName,  HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies == null) {
			return null;
		} else {
			int len = cookies.length;
			
			for(int i = 0; i < len; i++) {
				Cookie cookie = cookies[i];
				if(cookieName.equalsIgnoreCase(cookie.getName())) {
					return cookie;
				}
			}
		}
		return null;
	}
	
	public String getTicket(HttpServletRequest request) {
		String ticket = "";
		Cookie cookie = this.getCookie("ticket", request);
		if(cookie != null) {
			ticket = cookie.getValue();
		}
		return ticket;
	}
}
