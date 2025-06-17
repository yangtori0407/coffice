package com.coffice.app.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.coffice.app.chat.ChatService;
import com.coffice.app.posts.notice.NoticeService;
import com.coffice.app.users.UserVO;


import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping("/")
	public String home(@AuthenticationPrincipal UserVO userVO, Model model) throws Exception{
		
		if(userVO != null) {
			model.addAttribute("user", userVO);
		}
		
		model.addAttribute("list", noticeService.getMainList()) ;
		
		return "index";
	}
}
