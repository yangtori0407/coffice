package com.coffice.app.notification;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class NotificationController {
	
	private final SimpMessagingTemplate template;
	
	public NotificationController(SimpMessagingTemplate template) {
		this.template = template;
	}
	
	@MessageMapping("/notification")
	public void sendNotification() {
		log.info("알림!");
		template.convertAndSend("/sub/notification", "공지사항 알림");
	}
	
}
