package com.coffice.app.notification;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.coffice.app.attendance.AttendanceController;
import com.coffice.app.documents.DocumentVO;
import com.coffice.app.documents.lines.ReferenceLineVO;
import com.coffice.app.events.vacation.VacationVO;
import com.coffice.app.message.MessageVO;
import com.coffice.app.posts.board.BoardVO;
import com.coffice.app.posts.board.CommentVO;
import com.coffice.app.posts.notice.NoticeVO;
import com.coffice.app.users.UserVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationService {

    private final AttendanceController attendanceController;

	@Autowired
	private NotificationDAO notificationDAO;

	private final SimpMessagingTemplate template;

	public NotificationService(SimpMessagingTemplate template, AttendanceController attendanceController) {
		this.template = template;
		this.attendanceController = attendanceController;
	}

	@Transactional
	public void sendNotice(NoticeVO noticeVO) throws Exception {
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
		for (UserVO u : users) {
			info.put("userId", u.getUserId());
			info.put("notiNum", notificationVO.getNotiNum());
			notificationDAO.addNotiCheck(info);
		}

		template.convertAndSend("/sub/notice", notificationVO);
	}

	public void sendComment(CommentVO commentVO, BoardVO boardVO) throws Exception {
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
		notificationDAO.addNotiCheck(info);

		if (!boardVO.getUserId().equals(commentVO.getUserId())) {

			template.convertAndSend("/sub/notification/user." + boardVO.getUserId(), notificationVO);
		}

	}

	public void sendReply(CommentVO commentVO, CommentVO p) throws Exception {
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
		notificationDAO.addNotiCheck(info);
		// 댓글이 삭제되지 않고, 해당 댓글 사람이 답글을 다는 경우는 안보냄
		log.info("commentVO userId : {}", commentVO.getUserId());
		log.info("PVO userId : {}", p.getUserId());

		if (p.getDeleteStatus() != 1 && !commentVO.getUserId().equals(p.getUserId())) {
			log.info("대댓글 대댓글");
			template.convertAndSend("/sub/notification/user." + p.getUserId(), notificationVO);
		}
	}

	// 휴가 알림
	public void sendVaction(String contents, String receiver) throws Exception {
		NotificationVO notificationVO = new NotificationVO();
		notificationVO.setNotiContents(contents); // 원하는 내용으로 바꾸시면 됩니다~!
		LocalDateTime now = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(now);
		notificationVO.setNotiDate(timestamp);
		notificationVO.setRelateEntity("VACATION");
		notificationVO.setNotiKind("VACATION");

		notificationDAO.add(notificationVO);

		Map<String, Object> info = new HashMap<>();
		info.put("userId", receiver);
		info.put("notiNum", notificationVO.getNotiNum());
		notificationDAO.addNotiCheck(info);

		template.convertAndSend("/sub/notification/user." + receiver, notificationVO);
	}

	public void sendMessage(MessageVO messageVO, String userId) throws Exception {
		NotificationVO notificationVO = new NotificationVO();
		notificationVO.setNotiDate(messageVO.getSendDate());
		notificationVO.setNotiContents(messageVO.getMessageTitle());
		notificationVO.setNotiKind("MESSAGE");
		notificationVO.setRelateEntity("MESSAGE");
		notificationVO.setRelateId(messageVO.getMessageNum());
		notificationDAO.add(notificationVO);

		Map<String, Object> info = new HashMap<>();
		info.put("userId", userId);
		info.put("notiNum", notificationVO.getNotiNum());
		notificationDAO.addNotiCheck(info);

		template.convertAndSend("/sub/notification/user." + userId, notificationVO);
	}

	public void sendApprovalLine(DocumentVO documentVO, String userId, int status) throws Exception {
		NotificationVO notificationVO = new NotificationVO();
		LocalDateTime now = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(now);
		notificationVO.setNotiDate(timestamp);
		if(status == 0) {
			notificationVO.setNotiKind("DONE");
		} else if(status == 1) {
			notificationVO.setNotiKind("APPROVAL");
		} else {
			notificationVO.setNotiKind("REJECT");
		}
		notificationVO.setRelateEntity("APPROVAL");
		notificationVO.setRelateId(documentVO.getDocumentId()); //리다이렉트 할 때 쓸 문서 id
		//notificationVO.setNotiContents(documentVO.getTitle()); //결재 문서 제목

		// 알림 저장
		notificationDAO.add(notificationVO);

		// 알림을 보내야 할 사람 저장
		//결재는 다음 승인자 id만 보내주세요
		Map<String, Object> info = new HashMap<>();
		info.put("userId", userId);
		info.put("notiNum", notificationVO.getNotiNum());
		notificationDAO.addNotiCheck(info);
		//다음 참조자에게 가는 알림
		template.convertAndSend("/sub/notification/user." + userId, notificationVO);
	}

	public void sendReferrenceLine(DocumentVO documentVO, List<ReferenceLineVO> users) throws Exception {
		NotificationVO notificationVO = new NotificationVO();
		LocalDateTime now = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(now);
		notificationVO.setNotiDate(timestamp);
		notificationVO.setNotiKind("REFERENCE");
		notificationVO.setRelateEntity("REFERENCE");
		notificationVO.setRelateId(documentVO.getDocumentId()); //리다이렉트 할 때 쓸 문서 id
		//notificationVO.setNotiContents(null); //결재 문서 제목

		// 알림 저장
		notificationDAO.add(notificationVO);

		// 알림을 보내야 할 사람 저장
		// 참조자가 여러명이면 list 형태로 보내주세요
		Map<String, Object> info = new HashMap<>();
		for (ReferenceLineVO u : users) {
			info.put("userId", u.getUserId());
			info.put("notiNum", notificationVO.getNotiNum());
			notificationDAO.addNotiCheck(info);
			//참조자에게 알림이 가는 코드
			template.convertAndSend("/sub/notification/user." + u.getUserId(), notificationVO);
		}
	}

	public Map<String, Object> getNotification(String userId) throws Exception {
		Map<String, Object> result = new HashMap<>();
		int total = notificationDAO.getNonReadNotification(userId);
		List<NotificationVO> list = notificationDAO.getNotification(userId);
		result.put("total", total);
		result.put("list", list);

		return result;
	}

	public int updateNotiStatus(Long notiNum, String name) throws Exception {
		Map<String, Object> info = new HashMap<>();
		info.put("notiNum", notiNum);
		info.put("userId", name);
		return notificationDAO.updateNotiStatus(info);

	}

	public List<NotificationVO> moreNotification(Long notiCheckNum, String name) throws Exception {
		Map<String, Object> info = new HashMap<>();
		info.put("notiCheckNum", notiCheckNum);
		info.put("userId", name);
		// log.info("notiCheckNum: {}", notiCheckNum);
		// log.info("userId : {}", name);
		return notificationDAO.moreNotification(info);
	}

	public void checkNoticeNotification(NoticeVO noticeVO) throws Exception{
		NotificationVO notificationVO = new NotificationVO();
		notificationVO.setNotiKind("NOTICE");
		notificationVO.setRelateId(noticeVO.getNoticeNum());
		notificationVO = notificationDAO.getNoticeNotificationDetail(notificationVO);
		
		if(notificationVO != null) {
			Map<String, Object> info = new HashMap<>();
			info.put("notiNum", notificationVO.getNotiNum());
			info.put("userId", noticeVO.getUserId());
			
			notificationDAO.updateNotiStatus(info);
			
		}
	}
	
	@Transactional
	public void checkBoardNotification(BoardVO boardVO, String userId) throws Exception{
		NotificationVO notificationVO = new NotificationVO();
		notificationVO.setNotiKind("BOARD");
		notificationVO.setRelateId(boardVO.getBoardNum());
		List<NotificationVO> list = notificationDAO.getBoardNotificationDetail(notificationVO);
		if(list != null) {
			
			Map<String, Object> info = new HashMap<>();
			for(NotificationVO l : list) {
				//log.info("NotificationVO get : {}", l);
				info.put("notiNum", l.getNotiNum());
				info.put("userId", userId);
				notificationDAO.updateNotiStatus(info);
				
				info.clear();
			}
		}
		
	}
	@Transactional
	public void checkMessageNotification(MessageVO messageVO, String userId) throws Exception{
		log.info("메세지 읽음 처리 완료");
		NotificationVO notificationVO = new NotificationVO();
		notificationVO.setNotiKind("MESSAGE");
		notificationVO.setRelateId(messageVO.getMessageNum());
		
		notificationVO = notificationDAO.getNoticeNotificationDetail(notificationVO);
		log.info("{}", notificationVO);
		if(notificationVO != null) {
			
			Map<String, Object> info = new HashMap<>();
			info.put("notiNum", notificationVO.getNotiNum());
			info.put("userId", userId);
			notificationDAO.updateNotiStatus(info);
		}
		
	}

	public void checkVacation(String name) throws Exception{
		Map<String, Object> info = new HashMap<>();
		info.put("notiKind", "VACATION");
		info.put("userId", name);
		
		int result = notificationDAO.chekVacation(info);
		
	}

	public void checkDocumentNotification(String kind) {
		// TODO Auto-generated method stub
		
	}

}
