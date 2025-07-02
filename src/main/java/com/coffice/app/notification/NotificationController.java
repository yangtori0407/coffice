package com.coffice.app.notification;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coffice.app.users.UserVO;

import jdk.jfr.Threshold;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/notification/")
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;
	
	private final SimpMessagingTemplate template;
	
	public NotificationController(SimpMessagingTemplate template) {
		this.template = template;
	}
	
	@MessageMapping("/notification")
	public void sendNotification() {
		log.info("알림!");
		template.convertAndSend("/sub/notification", "공지사항 알림");
	}
	
	@GetMapping("getNotification")
	@ResponseBody
	public Map<String, Object> getNotification(Authentication authentication) throws Exception{
		String userId = authentication.getName();
		return notificationService.getNotification(userId);
	}
	
	@PostMapping("updateNotiStatus")
	public String updateNotiStatus(Long notiNum, Authentication authentication, Model model) throws Exception{
		int result = notificationService.updateNotiStatus(notiNum, authentication.getName());
		
		model.addAttribute("result", result);
		
		return "commons/ajaxResult";
	}
	@GetMapping("moreNotification")
	@ResponseBody
	public List<NotificationVO> moreNotification(Long notiCheckNum, Authentication authentication) throws Exception{
		//log.info("notiCheckNum: {}", notiCheckNum);
		List<NotificationVO> list = notificationService.moreNotification(notiCheckNum, authentication.getName());
		
		
		return list;
	}
	
	@PostMapping("checkVacation")
	public void CheckVacation(Authentication authentication) throws Exception{
		notificationService.checkVacation(authentication.getName());
	}
	
}
