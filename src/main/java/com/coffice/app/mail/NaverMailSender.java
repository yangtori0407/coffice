package com.coffice.app.mail;

import java.util.Properties;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;

@Component
public class NaverMailSender {
	
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private TemplateEngine templateEngine;
	
	
	
	public void sendEmail(String to, String authCode) throws Exception{
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		
		helper.setTo(to); //수신자 설정 
		helper.setFrom("ericalika@naver.com","Coffice");
		helper.setSubject("Coffice :: 비밀번호 재설정 인증 메일입니다"); //메일 제목 설정 
		
		Context context = new Context();
		context.setVariable("authCode" , authCode); //내용 설정
		
		String html = templateEngine.process("authEmail", context);
		helper.setText(html, true);
		
		mailSender.send(message);
		
	}

}
