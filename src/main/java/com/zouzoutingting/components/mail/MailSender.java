package com.zouzoutingting.components.mail;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import org.apache.log4j.Logger;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年3月14日
 */
public class MailSender {
	private static Logger logger = Logger.getLogger(MailSender.class);

	private static final Session normalSMTPSession = Session.getInstance(new Properties());

	private static final Session sslSMTPSession = Session.getInstance(new Properties());

	private static final Session tlsSMTPSession = Session.getInstance(new Properties());

	static {
		normalSMTPSession.getProperties().setProperty("mail.smtp.auth", "true");
		normalSMTPSession.getProperties().setProperty("mail.smtps.auth", "true");
		sslSMTPSession.getProperties().setProperty("mail.smtp.auth", "true");
		sslSMTPSession.getProperties().setProperty("mail.smtps.auth", "true");
		tlsSMTPSession.getProperties().setProperty("mail.smtp.auth", "true");
		tlsSMTPSession.getProperties().setProperty("mail.smtps.auth", "true");
		tlsSMTPSession.getProperties().setProperty("mail.smtp.starttls.enable","true");
	}

	public static void sendHtmlMail(SMTPSetting setting, MailMessage message)
			throws Exception {
		Session mailSenderSession = normalSMTPSession;

		String protocolName = "smtp";
		if (setting.getEnc().equals(SMTPSetting.SMTPEncrypt.SSL)) {
			mailSenderSession = sslSMTPSession;
			protocolName = "smtps";
			logger.trace("use SSL session");
		} else if (setting.getEnc().equals(SMTPSetting.SMTPEncrypt.TLS)) {
			mailSenderSession = tlsSMTPSession;
			logger.trace("user TLS session");
		} else {
			logger.trace("user NORMAL session");
		}
		logger.trace(String.format("protocol name is %s",
				new Object[] { protocolName }));

		Message javaMailMessage = message.toJavaHtmlMailMessage();

		long starttime = System.currentTimeMillis();

		Transport transport = mailSenderSession.getTransport(protocolName);
		transport.connect(setting.getSmtpHost(), setting.getPort(),
				setting.getUserName(), setting.getPwd());
		transport.sendMessage(javaMailMessage,
				javaMailMessage.getAllRecipients());
		transport.close();
		long endTime = System.currentTimeMillis();
		logger.trace(String.format(
				"send mail from %s to %s successfuly [%d milliseconds]",
				new Object[] { message.getForm(), message.getTo().get(0),
						Long.valueOf(endTime - starttime) }));
	}

	public static void sendMail(SMTPSetting setting, MailMessage message)
			throws Exception {
		Session mailSenderSession = normalSMTPSession;

		String protocolName = "smtp";
		if (setting.getEnc().equals(SMTPSetting.SMTPEncrypt.SSL)) {
			mailSenderSession = sslSMTPSession;
			protocolName = "smtps";
			logger.trace("use SSL session");
		} else if (setting.getEnc().equals(SMTPSetting.SMTPEncrypt.TLS)) {
			mailSenderSession = tlsSMTPSession;
			logger.trace("user TLS session");
		} else {
			logger.trace("user NORMAL session");
		}
		logger.trace(String.format("protocol name is %s",
				new Object[] { protocolName }));

		Message javaMailMessage = message.toJavaMailMessage();
		long starttime = System.currentTimeMillis();

		Transport transport = mailSenderSession.getTransport(protocolName);
		transport.connect(setting.getSmtpHost(), setting.getPort(),
				setting.getUserName(), setting.getPwd());
		transport.sendMessage(javaMailMessage,
				javaMailMessage.getAllRecipients());
		transport.close();
		long endTime = System.currentTimeMillis();
		logger.trace(String.format(
				"send mail from %s to %s successfuly [%d milliseconds]",
				new Object[] { message.getForm(), message.getTo().get(0),
						Long.valueOf(endTime - starttime) }));
	}

	public static Session getNormalSMTPSession() {
		return normalSMTPSession;
	}

	public static Session getSslSMTPSession() {
		return sslSMTPSession;
	}

	public static Session getTlsSMTPSession() {
		return tlsSMTPSession;
	}
}
