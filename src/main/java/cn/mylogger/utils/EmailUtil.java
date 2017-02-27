package cn.mylogger.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

/**
 * 简单的mail发送,只有TO
 * 
 * @author li-long
 */
public class EmailUtil {

	private static Properties mailProp;
	static {
		mailProp = PropertiesUtil.getProperties("mail.properties");
	}

	/**
	 * 
	 * @param subject
	 *            主题
	 * @param message
	 *            发送内容
	 * @param toEmails
	 *            要发送至的地址
	 */
	public static void send(String subject, String message, String... toEmails) {
		try {
			Properties prop = new Properties();
			prop.put("mail.host", mailProp.getProperty("mail.host"));
			prop.put("mail.smtp.auth", mailProp.getProperty("mail.smtp.auth"));
			String username = mailProp.getProperty("mail.username");
			String password = mailProp.getProperty("mail.password");
			Session session = Session.getDefaultInstance(prop,
					new Authenticator() {
						@Override
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username.substring(0, username.indexOf("@")),password);
						} 
					});
			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(username));
			for (String toEmail : toEmails) {
				msg.addRecipient(RecipientType.TO, new InternetAddress(toEmail));
			}
			msg.setSubject(subject);
			msg.setContent(message, "text/html;charset=utf-8");
			Transport.send(msg);
		} catch (Exception e) {
			System.out.println("邮件发送错误");
			e.printStackTrace();
		}
	}
}
