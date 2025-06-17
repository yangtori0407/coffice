package com.coffice.app.notification;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationService {
	
	private final SimpMessagingTemplate template;

	public NotificationService(SimpMessagingTemplate template) {
		this.template = template;
	}

	public void sendNotification() {
		log.info("알림!");
		template.convertAndSend("/sub/notification", "공지사항 알림");
	}
}
