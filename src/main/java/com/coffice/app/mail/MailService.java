package com.coffice.app.mail;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class MailService {
	
	@Autowired
	private NaverMailSender naverMailSender;
	
	public void sendAuthCode (String email, HttpSession session) throws Exception{
		String authCode = generateAuthCode();
		session.setAttribute("authCode", authCode);
		
		//인증코드 NaverMailSender로 전달 
		naverMailSender.sendEmail(email, authCode);
	}
	
	private String generateAuthCode() {
		int length = 6;
		
		String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lower = "abcdefghijklmnopqrstuvwxyz";
		String digits = "0123456789";
		String allChars = upper + lower + digits;
		
		Random random = new Random(System.currentTimeMillis());
		StringBuilder code = new StringBuilder();
		
		code.append(upper.charAt(random.nextInt(upper.length())));
		code.append(lower.charAt(random.nextInt(lower.length())));
		code.append(digits.charAt(random.nextInt(digits.length())));
		
		for (int i=3; i<length; i++) {
			code.append(allChars.charAt(random.nextInt(allChars.length())));
		}
		
		return shuffleString(code.toString(), random);
		
	}
	
	private String shuffleString(String input, Random random) {
		char[] characters = input.toCharArray();
		for (int i=0; i<characters.length; i++) {
			int j = random.nextInt(characters.length);
			
			char temp = characters[i];
			characters[i] = characters[j];
			characters[j] = temp;
		}
		return new String(characters);
	}
	
	

}
