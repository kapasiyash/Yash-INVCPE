package com.elitecore.cpe.util.notification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.elitecore.cpe.bl.facade.master.warehouse.WarehouseUtil;
import com.elitecore.cpe.util.logger.Logger;

public class NotificationUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MODULE = "NOTIFICATION_UTIL";
	
	
	
	public static String prepareCommaSeparatedStringfromList(List<String> list) {
		
		String retString = "";
		StringBuilder builder = new StringBuilder("");
		if(list!=null && !list.isEmpty()) {
			for(String data : list) {
				builder.append(data+",");
			}
			retString = builder.toString().substring(0, builder.toString().lastIndexOf(","));
		}
		
		return retString;
	}
	
	/*
	public static boolean sendEmailNotification(List<String> to,List<String> cc,String body, String subject,Map<String,String> valueMap ) {
		
		 StringBuffer reason = null;  
		try {
			
			String emailBody = prepareEmailBody(valueMap,body);
			String emailSubject = prepareEmailBody(valueMap,subject);
			final Properties propsEmailconfig =WarehouseUtil.readEmailProperty("EmailConfig");
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", propsEmailconfig.getProperty("smtp.host"));
			props.put("mail.smtp.port", propsEmailconfig.getProperty("smtp.port"));
			
			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(propsEmailconfig.getProperty("admin.emailId"),propsEmailconfig.getProperty("admin.passwd"));
						}
					});
			Message message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(propsEmailconfig.getProperty("admin.emailId")));
			
			if(to!=null && !to.isEmpty()) {
				javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[to.size()];
				for (int i = 0; i < to.size(); i++) {
					addressTo[i] = new javax.mail.internet.InternetAddress(to.get(i));
				}
				message.addRecipients(Message.RecipientType.TO,addressTo);
			}
			
			if(cc!=null && !cc.isEmpty()) {
				javax.mail.internet.InternetAddress[] addressCc = new javax.mail.internet.InternetAddress[cc.size()];
				for (int i = 0; i < cc.size(); i++) {
					addressCc[i] = new javax.mail.internet.InternetAddress(cc.get(i));
				}
				message.addRecipients(Message.RecipientType.CC,addressCc);
			}
			
			message.setSentDate(new Date());
			message.setSubject(emailSubject);
			message.setContent(emailBody.toString(), "text/html");
			Transport.send(message);
			Logger.logTrace(MODULE, "------------MAIL SENT---------------");
			Logger.logTrace(MODULE, "------------MAIL SENT---------------");
			
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			 StringBuffer exception = new StringBuffer(ex.getMessage() + "");  
			   
	            if (exception.indexOf("ConnectException") >= 0)      // connection problem.  
	            {  
	                reason = new StringBuffer(" Unable to Connect Mail server");  
	            }  
	            else if (exception.indexOf("SendFailedException") >= 0)      // Wrong To Address   
	            {  
	                reason = new StringBuffer("Wrong To Mail address");  
	            }  
	            else if (exception.indexOf("FileNotFoundException") >= 0)    //File Not Found at Specified Location  
	            {  
	                reason = new StringBuffer("File Not Found at Specific location");                     
	            }  
	            else        // Email has not been sent.  
	            {  
	                reason = new StringBuffer("Email has not been sent.");  
	            }
			
	            Logger.logTrace(MODULE, "------------MAIL NOT SENT :: "+reason.toString());
			return false;
		}
	}
	*/
	public static boolean sendEmailNotification(List<String> to,List<String> cc,String body, String subject,Map<String,String> valueMap,final String adminemaildId,final String adminpassword,String smtpHost,String smtpPort ) {
		
		 StringBuffer reason = null;  
		try {
			
			String emailBody = prepareEmailBody(valueMap,body);
			String emailSubject = prepareEmailBody(valueMap,subject);
		//	final Properties propsEmailconfig =WarehouseUtil.readEmailProperty("EmailConfig");
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
/*			props.put("mail.smtp.host", propsEmailconfig.getProperty("smtp.host"));
			props.put("mail.smtp.port", propsEmailconfig.getProperty("smtp.port"));
*/			props.put("mail.smtp.host",smtpHost);
			props.put("mail.smtp.port", smtpPort);

			
			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(adminemaildId,adminpassword);
						}
					});
			Message message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(adminemaildId));
			
			if(to!=null && !to.isEmpty()) {
				javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[to.size()];
				for (int i = 0; i < to.size(); i++) {
					addressTo[i] = new javax.mail.internet.InternetAddress(to.get(i));
				}
				message.addRecipients(Message.RecipientType.TO,addressTo);
			}
			
			if(cc!=null && !cc.isEmpty()) {
				javax.mail.internet.InternetAddress[] addressCc = new javax.mail.internet.InternetAddress[cc.size()];
				for (int i = 0; i < cc.size(); i++) {
					addressCc[i] = new javax.mail.internet.InternetAddress(cc.get(i));
				}
				message.addRecipients(Message.RecipientType.CC,addressCc);
			}
			
			message.setSentDate(new Date());
			message.setSubject(emailSubject);
			message.setContent(emailBody.toString(), "text/html");
			Transport.send(message);
			Logger.logTrace(MODULE, "------------MAIL SENT---------------");
			Logger.logTrace(MODULE, "------------MAIL SENT---------------");
			
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			 StringBuffer exception = new StringBuffer(ex.getMessage() + "");  
			   
	            if (exception.indexOf("ConnectException") >= 0)      // connection problem.  
	            {  
	                reason = new StringBuffer(" Unable to Connect Mail server");  
	            }  
	            else if (exception.indexOf("SendFailedException") >= 0)      // Wrong To Address   
	            {  
	                reason = new StringBuffer("Wrong To Mail address");  
	            }  
	            else if (exception.indexOf("FileNotFoundException") >= 0)    //File Not Found at Specified Location  
	            {  
	                reason = new StringBuffer("File Not Found at Specific location");                     
	            }  
	            else        // Email has not been sent.  
	            {  
	                reason = new StringBuffer("Email has not been sent.");  
	            }
			
	            Logger.logTrace(MODULE, "------------MAIL NOT SENT :: "+reason.toString());
			return false;
		}
	}
	public static void sendMail(Map<String,String> map, String template,String to,String cc, String subject) {
		
		
		try {
			
			String body = prepareEmailBody(map,template);
			
			final Properties propsEmailconfig =WarehouseUtil.readEmailProperty("EmailConfig");
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", propsEmailconfig.getProperty("smtp.host"));
			props.put("mail.smtp.port", propsEmailconfig.getProperty("smtp.port"));
			
			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(propsEmailconfig.getProperty("admin.emailId"),propsEmailconfig.getProperty("admin.passwd"));
						}
					});
			Message message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(propsEmailconfig.getProperty("admin.emailId")));
			message.addRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			if(cc!=null && !cc.isEmpty()) {
				message.addRecipients(Message.RecipientType.CC,InternetAddress.parse(cc));
			}
			message.setSentDate(new Date());
			message.setSubject(subject);
			message.setContent(body.toString(), "text/html");
			Transport.send(message);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}


	public static String prepareEmailBody(Map<String, String> map,
			String template) {

		String body = "";
		
		body = template;
		
		if(body!=null && !body.isEmpty()) {
			if(map!=null && !map.isEmpty()) {
				for(Entry<String, String> entry : map.entrySet()) {
					if(entry.getKey()!=null ) {
						
						if(entry.getValue()!=null) {
							body = body.replace(entry.getKey(), entry.getValue());
						} else {
							body = body.replace(entry.getKey(), "");
						}
					}
				}
			}
		}
		
		return body;
	}

	public static boolean sendSMSNotification(String smsBody,String smsURL, String mobileNumber) {
		Logger.logTrace(MODULE, "Inside sendSMSNotification");
		Logger.logTrace(MODULE,":::smsBody::"+smsBody);
		Logger.logTrace(MODULE,":::smsURL::"+smsURL);
		Logger.logTrace(MODULE,"::::mobileNumber::::"+mobileNumber);

		URL url;
		 URLConnection conn = null;
		 InputStream is;
		String strReturnStatus = null;
		String status;
		smsBody = smsBody.replaceAll("\\<.*?>","").replaceAll("&nbsp;", " ");
		String message=null;
			try {
				smsBody=URLEncoder.encode(smsBody, "UTF-8");
			
				if(smsURL!=null && mobileNumber!=null && !smsURL.isEmpty() && !mobileNumber.isEmpty()){
					message=smsURL+"&number="+mobileNumber+"&udata="+smsBody;
					url=new URL(message);
					Logger.logTrace(MODULE,"Send SMS Prepared url:::::"+url);
					conn=url.openConnection();
				    is = conn.getInputStream();
				    Logger.logTrace(MODULE, "connection got.....");
				    BufferedReader in = new BufferedReader(new InputStreamReader(is));
				    while ((status = in.readLine()) != null) {
				    	Logger.logTrace(MODULE,":::status:::"+status);
				    	strReturnStatus = status;
				    	Logger.logDebug("SMS CLIENT", "strReturnStatus"+strReturnStatus);
				    }
					in.close();
					if(strReturnStatus!=null){
						return true;
					}
				}
			}catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return false;
			}catch (MalformedURLException e) {
				e.printStackTrace();
				return false;
			}catch (IOException e) {
				e.printStackTrace();
				return false;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		return false;
	}

}

