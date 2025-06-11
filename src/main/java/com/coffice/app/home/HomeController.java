package com.coffice.app.home;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.coffice.app.users.UserVO;


import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Value("${calendar.api.key}")
	private String apiKey;

	@ModelAttribute("apiKey")
	public String getKey() {
		return this.apiKey;
	}
	
	@GetMapping("/")
	public String home(@AuthenticationPrincipal UserVO userVO, Model model) {
		
		if(userVO != null) {
			model.addAttribute("user", userVO);
		}
		return "index";
	}
}
