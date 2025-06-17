package com.coffice.app.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.coffice.app.posts.notice.NoticeVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationService {
	
	@Autowired
	private NotificationDAO notificationDAO;
	
	private final SimpMessagingTemplate template;

	public NotificationService(SimpMessagingTemplate template) {
		this.template = template;
	}

	public void sendNotice(NoticeVO noticeVO) throws Exception{
		log.info("알림!");
		NotificationVO notificationVO = new NotificationVO();
		notificationVO.setNotiContents(noticeVO.getNoticeTitle());
		notificationVO.setRelateId(noticeVO.getNoticeNum());
		notificationVO.setNotiDate(noticeVO.getNoticeDate());
		notificationVO.setNotiKind("NOTICE");
		notificationVO.setRelateEntity("NOTICE");
		notificationDAO.add(notificationVO);
		template.convertAndSend("/sub/notice", notificationVO);
	}
}
