package com.coffice.app.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.coffice.app.branch.BranchService;
import com.coffice.app.documents.DocumentService;
import com.coffice.app.posts.notice.NoticeService;
import com.coffice.app.users.UserVO;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
public class HomeController {
	
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private DocumentService documentService;
	
	
	@GetMapping("/")

	public String home(@AuthenticationPrincipal UserVO userVO, Model model, HttpSession session) throws Exception{
		
		model.addAttribute("lastTemp", documentService.getLastTemp(session));
		model.addAttribute("getlastHandled", documentService.getlastHandled(session));
		model.addAttribute("getTodayReference", documentService.getTodayReference(session));
		model.addAttribute("getTodayWaiting", documentService.getTodayWaiting(session));
		
		model.addAttribute("list", noticeService.getMainList()) ;
		model.addAttribute("chart", branchService.getTotalChart());
		model.addAttribute("registerBranch", branchService.registerBranch());
		model.addAttribute("kind", "홈");

		return "index";
	}
}
