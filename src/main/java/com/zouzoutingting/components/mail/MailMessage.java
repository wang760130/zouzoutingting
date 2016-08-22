package com.zouzoutingting.components.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年3月14日
 */
public class MailMessage {
	public static final String SEPARATOR = ":";
	public static final String USUALLY_ERROR_SEPARATOR = "：";
	
	private String from;
	private List<String> to = new ArrayList<String>();

	private List<String> cc = new ArrayList<String>();

	private List<String> bcc = new ArrayList<String>();
	private Date date;
	private String subject;
	private String body;
	private String[] arrArchievList = null;
	private int size;
	private String priority = "3";
	private List<String> replyList = new ArrayList<String>();

	public String[] getArrArchievList() {
		return this.arrArchievList;
	}

	public void setArrArchievList(String[] arrArchievList) {
		this.arrArchievList = arrArchievList;
	}

	public String getForm() {
		return this.from;
	}

	public void setForm(String from) {
		this.from = from;
	}

	public List<String> getTo() {
		return this.to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public List<String> getCc() {
		return this.cc;
	}

	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	public List<String> getBcc() {
		return this.bcc;
	}

	public void setBcc(List<String> bcc) {
		this.bcc = bcc;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public List<String> getReplyList() {
		return this.replyList;
	}

	public void setReplyList(List<String> list) {
		this.replyList = list;
	}

	protected Message toJavaHtmlMailMessage() throws Exception {
		Message message = new MimeMessage(MailSender.getNormalSMTPSession());
		message.setSubject(this.subject);
		message.setSentDate(new Date());
		message.setHeader("X-Priority", this.priority);

		message.setFrom(toAddress(this.from));

		message.setReplyTo(toAddressArray(this.replyList));

		message.setRecipients(Message.RecipientType.TO, toAddressArray(this.to));

		message.setRecipients(Message.RecipientType.CC, toAddressArray(this.cc));

		message.setRecipients(Message.RecipientType.BCC,
				toAddressArray(this.bcc));

		if ((this.arrArchievList != null) && (this.arrArchievList.length > 0)) {
			MimeMultipart mainPart = new MimeMultipart();

			BodyPart html = new MimeBodyPart();
			html.setContent(this.body, "text/html; charset=GBK");
			mainPart.addBodyPart(html);

			mainPart.setSubType("related");

			for (int index = 0; index < this.arrArchievList.length; index++) {
				File file = new File(this.arrArchievList[index]);
				MimeBodyPart mailArchieve = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(
						this.arrArchievList[index]);
				mailArchieve.setDataHandler(new DataHandler(fds));
				mailArchieve.setFileName(MimeUtility.encodeText(fds.getName(),
						"GBK", "B"));
				mainPart.addBodyPart(mailArchieve);
			}

			message.setContent(mainPart);
		} else {
			Multipart mainPart = new MimeMultipart();
			BodyPart html = new MimeBodyPart();
			html.setContent(this.body, "text/html; charset=GBK");
			mainPart.addBodyPart(html);
			message.setContent(mainPart);
		}

		return message;
	}

	protected Message toJavaMailMessage() throws Exception {
		Message message = new MimeMessage(MailSender.getNormalSMTPSession());
		message.setSubject(this.subject);
		message.setSentDate(new Date());
		message.setHeader("X-Priority", this.priority);

		message.setFrom(toAddress(this.from));

		message.setReplyTo(toAddressArray(this.replyList));

		message.setRecipients(Message.RecipientType.TO, toAddressArray(this.to));

		message.setRecipients(Message.RecipientType.CC, toAddressArray(this.cc));

		message.setRecipients(Message.RecipientType.BCC,
				toAddressArray(this.bcc));
		MimeMultipart mmp = new MimeMultipart("alternative");

		message.setContent(mmp);
		MimeBodyPart part = new MimeBodyPart();
		part.setText(this.body, "UTF-8");
		mmp.addBodyPart(part);

		return message;
	}

	private static Address toAddress(String addressStr) throws Exception {
		if (StringUtils.isBlank(addressStr)) {
			return null;
		}

		addressStr = addressStr.replace("：", ":");
		String personal = substringBefore(addressStr, ":");
		String address = substringAfter(addressStr, ":");
		if (StringUtils.isBlank(address)) {
			address = addressStr;
		}
		Address javaMailAddress = new InternetAddress(address, personal);
		return javaMailAddress;
	}

	private static Address[] toAddressArray(List<String> list) throws Exception {
		List<Address> addressList = new ArrayList<Address>();
		if (list == null) {
			return null;
		}
		for (String str : list) {
			addressList.add(toAddress(str));
		}
		return (Address[]) addressList.toArray(new Address[0]);
	}

	public static String substringBefore(String source, String str) {
		if (str == null)
			return source;
		if ("".equals(str)) {
			return "";
		}
		int index = source.indexOf(str);
		if (index == -1) {
			return "";
		}
		return StringUtils.substring(source, 0, index);
	}

	public static String substringAfter(String source, String str) {
		if (source == null) {
			return null;
		}
		if (str == null)
			return "";
		if ("".equals(str)) {
			return source;
		}
		int index = source.indexOf(str);
		if (index == -1) {
			return "";
		}
		return StringUtils.substring(source, index + 1, source.length());
	}
}
