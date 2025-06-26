package com.coffice.app.notification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coffice.app.events.vacation.VacationVO;
import com.coffice.app.posts.board.BoardVO;
import com.coffice.app.posts.board.CommentVO;
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
	
	public void sendComment(CommentVO commentVO, BoardVO boardVO) throws Exception{
		NotificationVO notificationVO = new NotificationVO();
		notificationVO.setNotiContents(boardVO.getBoardTitle());
		notificationVO.setNotiDate(commentVO.getCommentDate());
		notificationVO.setNotiKind("BOARD");
		notificationVO.setRelateEntity("BOARD");
		notificationVO.setRelateId(commentVO.getBoardNum());
		
		notificationDAO.add(notificationVO);
		
		Map<String, Object> info = new HashMap<>();
		info.put("userId", boardVO.getUserId());
		info.put("notiNum", notificationVO.getNotiNum());
		notificationDAO.addNoticeCheck(info);
		
		if(!boardVO.getUserId().equals(commentVO.getUserId())) {
			
			template.convertAndSend("/sub/notification/user."+ boardVO.getUserId(), notificationVO);
		}
		
	}
	
	public void sendReply(CommentVO commentVO, CommentVO p) throws Exception{
		NotificationVO notificationVO = new NotificationVO();
		notificationVO.setNotiContents(p.getCommentContents());
		notificationVO.setNotiDate(commentVO.getCommentDate());
		notificationVO.setNotiKind("REPLY");
		notificationVO.setRelateEntity("BOARD");
		notificationVO.setRelateId(p.getBoardNum());
		
		notificationDAO.add(notificationVO);
		Map<String, Object> info = new HashMap<>();
		info.put("userId", p.getUserId());
		info.put("notiNum", notificationVO.getNotiNum());
		notificationDAO.addNoticeCheck(info);
		//댓글이 삭제되지 않고, 해당 댓글 사람이 답글을 다는 경우는 안보냄
		log.info("commentVO userId : {}", commentVO.getUserId());
		log.info("PVO userId : {}", p.getUserId());
		
		if(p.getDeleteStatus() != 1 && !commentVO.getUserId().equals(p.getUserId())){
			log.info("대댓글 대댓글");
			template.convertAndSend("/sub/notification/user."+ p.getUserId(), notificationVO);
		}
	}
	
	public void sendVaction(VacationVO vacationVO) throws Exception{
		NotificationVO notificationVO = new NotificationVO();
		notificationVO.setNotiContents("휴가 신청");
//		notificationVO.setNotiDate();
	}

	public Map<String, Object> getNotification(String userId) throws Exception{
		Map<String, Object> result = new HashMap<>();
		int total = notificationDAO.getNonReadNotification(userId);
		List<NotificationVO> list = notificationDAO.getNotification(userId);
		result.put("total", total);
		result.put("list", list);
		
		return result;
	}

	public int updateNotiStatus(Long notiNum, String name) throws Exception{
		Map<String, Object> info = new HashMap<>();
		info.put("notiNum", notiNum);
		info.put("userId", name);
		return notificationDAO.updateNotiStatus(info);
		
	}

	public List<NotificationVO> moreNotification(Long notiCheckNum, String name) throws Exception{
		Map<String, Object> info = new HashMap<>();
		info.put("notiCheckNum", notiCheckNum);
		info.put("userId", name);
		//log.info("notiCheckNum: {}", notiCheckNum);
		//log.info("userId : {}", name);
		return notificationDAO.moreNotification(info);
	}

	


	
}
