package com.lanouit.exam.util.mail;

import java.net.MalformedURLException;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.lanouit.exam.po.User;

public class Mail {

	private static final String HOST_NAME = "smtp.163.com";

	private static final String FROM_MAIL = "18826107119@163.com";

	private static final String FROM_NAME = "不知什么名字的服创队伍";

	private static final String USER_NAME = "18826107119@163.com";

	private static final String PASS_WORD = "kenken34082521";

	/**
	 * 发送HTML邮件
	 * 
	 * @param toMail
	 * @param htmlMsg
	 * @throws EmailException
	 */
	public void sendHtmlEmail(String toMail, String htmlMsg, String subject)
			throws EmailException {

		HtmlEmail htmlEmail = new HtmlEmail();
		htmlEmail.setHostName(HOST_NAME);
		htmlEmail.setSmtpPort(465);
		htmlEmail.setCharset("UTF-8");
		htmlEmail.setAuthenticator(new DefaultAuthenticator(USER_NAME,
				PASS_WORD));
		htmlEmail.setSSLOnConnect(true);
		htmlEmail.addTo(toMail);
		htmlEmail.setFrom(FROM_MAIL, FROM_NAME);
		htmlEmail.setSubject(subject);
		htmlEmail.setHtmlMsg(htmlMsg);
		htmlEmail
				.setTextMsg("Your email client does not support HTML messages");
		htmlEmail.send();

	}

	/**
	 * 发送用户注册邮箱激活email
	 * 
	 * @param user
	 * @throws MalformedURLException
	 * @throws EmailException
	 */
	public void sendRegisterEmail(User user) throws MalformedURLException,
			EmailException {
		// 构造url
		String strUrl = "http://127.0.0.1:8080/exam/user/activateUser?id="
				+ user.getId() + "&userToken=" + user.getUserToken()
				+ "&time=" + user.getUserCreatetime().getTime();
		// 构造内容
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<h3>蓝鸥IT教育课程考评-用户注册</h3>");
		stringBuffer.append("<h4>邮箱验证</h4>");
		stringBuffer.append("<a href=\"" + strUrl + "\">点击激活</a>");
		String htmlMsg = stringBuffer.toString();
		String subject = "蓝鸥IT教育课程考评-用户注册";
		sendHtmlEmail(user.getUserMail(), htmlMsg, subject);
	}
	
}
