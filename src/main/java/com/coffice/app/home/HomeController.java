package com.coffice.app.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.coffice.app.branch.BranchService;
import com.coffice.app.posts.notice.NoticeService;
import com.coffice.app.users.UserVO;


import lombok.extern.slf4j.Slf4j;

@Controller
public class HomeController {
	
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private BranchService branchService;
	
	@GetMapping("/")

	public String home(@AuthenticationPrincipal UserVO userVO, Model model) throws Exception{
		
		if(userVO != null) {
			model.addAttribute("user", userVO);
		}
		
		model.addAttribute("list", noticeService.getMainList()) ;
		model.addAttribute("chart", branchService.getTotalChart());

		return "index";
	}
}
