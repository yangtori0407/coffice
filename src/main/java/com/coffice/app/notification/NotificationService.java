package com.coffice.app.notification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coffice.app.posts.notice.NoticeVO;
import com.coffice.app.users.UserVO;

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
	
	@Transactional
	public void sendNotice(NoticeVO noticeVO) throws Exception{
		log.info("알림!");
		NotificationVO notificationVO = new NotificationVO();
		notificationVO.setNotiContents(noticeVO.getNoticeTitle());
		notificationVO.setRelateId(noticeVO.getNoticeNum());
		notificationVO.setNotiDate(noticeVO.getNoticeDate());
		notificationVO.setNotiKind("NOTICE");
		notificationVO.setRelateEntity("NOTICE");
		notificationDAO.add(notificationVO);
		
		List<UserVO> users = notificationDAO.getUserList(noticeVO.getUserId());
		Map<String, Object> info = new HashMap<>();
		for(UserVO u : users) {
			info.put("userId", u.getUserId());
			info.put("notiNum", notificationVO.getNotiNum());
			notificationDAO.addNoticeCheck(info);
		}
		
		template.convertAndSend("/sub/notice", notificationVO);
	}

	public List<NotificationVO> getNotification(String userId) throws Exception{
		return notificationDAO.getNotification(userId);
		
	}

	public int updateNotiStatus(Long notiNum, String name) throws Exception{
		Map<String, Object> info = new HashMap<>();
		info.put("notiNum", notiNum);
		info.put("userId", name);
		return notificationDAO.updateNotiStatus(info);
		
	}
}
