package com.nights.retarded.utils;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailUtils {
	
    private static JavaMailSender javaMailSender;
	
    @Resource
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		MailUtils.javaMailSender = javaMailSender;
	}

    public static void sendEmails(String tos, String subject, String content) throws Exception {
    	
    	for (String to : tos.split(",")) {
    		MimeMessage message = javaMailSender.createMimeMessage();
    		MimeMessageHelper helper = new MimeMessageHelper(message, true);
    		helper.setFrom("jiachi1207@foxmail.com");
    		helper.setTo(to);
    		helper.setSubject(subject);
    		helper.setText(content, true);
    		javaMailSender.send(message);
		}
	    
	}
	
}
