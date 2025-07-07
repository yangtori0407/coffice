package com.coffice.app.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/mail/*")
public class MailController {
	
	@Autowired
	private MailService mailService;
	
	@PostMapping("mailsend")
	@ResponseBody
	public String mailSend(@RequestParam String email, @RequestParam String userId, HttpSession session) {
		
		try {
			session.setAttribute("resetEmail", email);
	        session.setAttribute("resetUserId", userId);
			
			mailService.sendAuthCode(email, session);
			return "메일 전송 완료";
		} catch (Exception e) {
			e.printStackTrace();
			return "실패: " + e.getMessage();
		}
	}

}
