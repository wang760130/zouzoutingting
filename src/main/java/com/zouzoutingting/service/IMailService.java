package com.zouzoutingting.service;

/**
 * 发邮件类
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年8月10日
 */
public interface IMailService {
	
	public boolean send(String subject, String body, String[] arrArchievList, String[] mails);
	
}
