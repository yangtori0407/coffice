package com.coffice.app.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coffice.app.users.UserDAO;
import com.coffice.app.users.UserVO;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class MessageService {

	@Autowired
	private MessageDAO messageDAO;
	
	@Autowired
	private UserDAO userDAO;

	private final JavaMailSender messageMailSender;

	public MessageService(JavaMailSender messageMailSender) {
		this.messageMailSender = messageMailSender;
	}
	
	@Transactional
	public int sendMessage(MessageVO messageVO, String[] receivers,String userId) throws Exception {
		//이메일 내용 저장
		messageVO.setSender(userId);
		int result = messageDAO.add(messageVO);
		
		Map<String, Object> info = new HashMap<>();
		//로그인 사용자의 이메일을 가지고 오기 위한 로직
		UserVO userVO = new UserVO();
		userVO.setUserId(userId);
		userVO = userDAO.detail(userVO);
		
		//kind 
		//1 : 외부 이메일 0 : 내부 이메일
		for(String s : receivers) {
			info.put("userId", s);
			info.put("messageNum", messageVO.getMessageNum());
			if(s.contains("@")) {
				info.put("kind", 1);
				//이메일 받을 유저 저장
				messageDAO.addEmailUser(info);
				System.out.println("메일 주소: [" + s + "]");
				sendMessageMail(messageVO, s.trim(), userVO);
			}else {
				info.put("kind", 0);
				//이메일 받을 유저 저장
				messageDAO.addEmailUser(info);
			}
			info.clear();
		}
	

		return result;
	}

	private void sendMessageMail(MessageVO messageVO, String receiverEmail, UserVO userVO) throws Exception {
		MimeMessage mimeMessage = messageMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

		helper.setTo(receiverEmail);
		helper.setSubject(messageVO.getMessageTitle());
		helper.setText(messageVO.getMessageContents(), true);
		String personal ="Coffice " + userVO.getDeptName() + " " + userVO.getName(); // 예: "영업팀 홍길동"
		InternetAddress from = new InternetAddress("cofficehr@gmail.com", personal, "UTF-8");
		helper.setFrom(from);

		helper.setReplyTo(userVO.getEmail().trim());

		messageMailSender.send(mimeMessage);
	}

	public List<MessageVO> getSendMessage(String userId) throws Exception {
		return messageDAO.getSendMessage(userId);
	}

	public List<MessageVO> getReceiveMessage(String userId) throws Exception{

		return messageDAO.getReceiveMessage(userId);
		
	}

	public MessageVO detail(MessageVO messageVO) throws Exception{
		return messageDAO.detail(messageVO);
	}
}
