package com.zouzoutingting.service.impl;

import org.apache.log4j.Logger;

import com.zouzoutingting.components.mail.MailMessage;
import com.zouzoutingting.components.mail.MailSender;
import com.zouzoutingting.components.mail.SMTPSetting;
import com.zouzoutingting.service.IMailService;

/**
 * 发邮件类
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年8月10日
 */
public class MailServiceImpl implements IMailService {
	
	private static Logger logger = Logger.getLogger(MailServiceImpl.class);

	private static final String smtpHost = "smtp.qq.com";
	private static final String userName = "";
	private static final String pwd = "";
	
	@Override
	public boolean send(String subject, String body,
			String[] arrArchievList, String[] mails) {
		boolean result = false;
		
		SMTPSetting setting = new SMTPSetting();
		setting.setSmtpHost(smtpHost);
		setting.setPort(25);
		setting.setUserName(userName);
		setting.setPwd(pwd);

		MailMessage message = new MailMessage();
		message.setForm(userName);
		message.setSubject(subject);
		message.setBody(body);
		message.setPriority("3");
		message.setArrArchievList(arrArchievList);

		String mailsMsg = "";
		for (String mail : mails) {
			message.getTo().add(mail);
			mailsMsg = mailsMsg + mail;
		}
		try {
			MailSender.sendHtmlMail(setting, message);
			result = true;
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		
		return result;
	}
}
