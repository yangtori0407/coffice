package com.coffice.app.message;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.coyote.http11.upgrade.UpgradeServletOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.files.FileManager;
import com.coffice.app.files.FileVO;
import com.coffice.app.notification.NotificationService;
import com.coffice.app.page.Pager;
import com.coffice.app.posts.notice.NoticeFilesVO;
import com.coffice.app.users.UserDAO;
import com.coffice.app.users.UserVO;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageService {

	@Autowired
	private MessageDAO messageDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private FileManager fileManager;
	
	@Value("${app.files.base}")
	private String path;

	private final JavaMailSender messageMailSender;

	public MessageService(JavaMailSender messageMailSender) {
		this.messageMailSender = messageMailSender;
	}
	
	@Transactional
	public int sendMessage(MessageVO messageVO, String[] receivers,String userId, MultipartFile[] attaches) throws Exception {
		//이메일 내용 저장
		messageVO.setSender(userId);
		int result = messageDAO.add(messageVO);
		
		List<MessageFilesVO> files = new ArrayList<>();
		if(attaches!= null && attaches.length > 0) {
			
			for(MultipartFile f : attaches) {
				MessageFilesVO filesVO = new MessageFilesVO();
				String fileName = fileManager.fileSave(path.concat("message"), f);
				filesVO.setOriginName(f.getOriginalFilename());
				filesVO.setSaveName(fileName);
				filesVO.setMessageNum(messageVO.getMessageNum());
				files.add(filesVO);
				messageDAO.saveFile(filesVO);
			}
		}
		
		log.info("messageVO 체크 : {}", result);
		
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
				sendMessageMail(messageVO, s.trim(), userVO, files);
			}else {
				info.put("kind", 0);
				//이메일 받을 유저 저장
				messageDAO.addEmailUser(info);
			}
			messageVO = messageDAO.detail(messageVO);
			notificationService.sendMessage(messageVO, s);
			info.clear();
		}
		

		return result;
	}

	private void sendMessageMail(MessageVO messageVO, String receiverEmail, UserVO userVO, List<MessageFilesVO> attaches) throws Exception {
		MimeMessage mimeMessage = messageMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

		helper.setTo(receiverEmail);
		helper.setSubject(messageVO.getMessageTitle());
		helper.setText(messageVO.getMessageContents(), true);
		String personal ="Coffice " + userVO.getDeptName() + " " + userVO.getName(); // 예: "영업팀 홍길동"
		InternetAddress from = new InternetAddress("cofficehr@gmail.com", personal, "UTF-8");
		helper.setFrom(from);

		helper.setReplyTo(userVO.getEmail().trim());
		
		for(MessageFilesVO m : attaches) {
			File file = new File(path.concat("message/").concat(m.getSaveName()));
			log.info("files : {}", file.getPath());
			if (file.exists()) {
			    helper.addAttachment(m.getOriginName(), file);
			} else {
			    log.info("파일이 존재하지 않습니다!");
			}
		}

		messageMailSender.send(mimeMessage);
	}

	public List<MessageVO> getSendMessage(String userId, Pager pager) throws Exception {
		Map<String, Object> info = new HashMap<>();
		info.put("userId", userId);
		info.put("pager", pager);
		pager.make();
		Long total = messageDAO.getSendTotal(info);
		pager.makeNum(total);
		
		return messageDAO.getSendMessage(info);
	}

	public List<MessageVO> getReceiveMessage(String userId, Pager pager) throws Exception{
		Map<String, Object> info = new HashMap<>();
		info.put("userId", userId);
		info.put("pager", pager);
		pager.make();
		Long total = messageDAO.getReceiveTotal(info);
		pager.makeNum(total);
		return messageDAO.getReceiveMessage(info);
	}

	public MessageVO detail(MessageVO messageVO) throws Exception{
		return messageDAO.detail(messageVO);
	}

	public int receiveDelete(Long messageNum, String userId) throws Exception{
		Map<String, Object> info = new HashMap<>();
		info.put("messageNum", messageNum);
		info.put("userId", userId);
		
		return messageDAO.receiveDelete(info);
		
	}

	public int sendDelete(Long messageNum, String userId) throws Exception{
		Map<String, Object> info = new HashMap<>();
		info.put("messageNum", messageNum);
		info.put("userId", userId);
		
		return messageDAO.sendDelete(info);
	}

	public MessageVO reply(Long messageNum, String userId) throws Exception{
		MessageVO messageVO = new MessageVO();
		messageVO.setMessageNum(messageNum);
		messageVO = detail(messageVO);
		UserVO userVO = messageDAO.getUserInfo(userId);
		
		String message = "<div><br></div>"
			    + "<p style=\"font-size:10pt;font-family:sans-serif;padding:0 0 0 10pt\">"
			    + "<span>-----Original Message-----</span><br>"
			    + "<b>From:</b> &quot;Coffice " + messageVO.getSenderDept() + " " + messageVO.getSenderName() + "&quot; "
			    + "&lt;" + messageVO.getEmail() + "&gt;<br>"
			    + "<b>To:</b> " + userVO.getDeptName() + " " + userVO.getName() 
			    + " &lt;" + userVO.getEmail() + "&gt;<br>"
			    + "<b>Cc:</b><br>"
			    + "<b>Sent:</b> " + messageVO.getSendDate() + "<br>"
			    + "<b>Subject:</b> " + messageVO.getMessageTitle() + "<br>"
			    + "</p><p>" + messageVO.getMessageContents() + "</p><p><br></p>";
		
		messageVO.setMessageContents(message);
		String messageTitle = "Re:" + messageVO.getMessageTitle();
		messageVO.setMessageTitle(messageTitle);
		
		return messageVO;
	}

	public MessageVO getCheck(MessageVO messageVO) throws Exception{
		
		return messageDAO.detail(messageVO);
	}

	public int read(Long messageNum, String userId) throws Exception{
		Map<String, Object> info = new HashMap<>();
		info.put("userId", userId);
		info.put("messageNum", messageNum);
		
		return messageDAO.read(info);
		
	}

	public FileVO fileDown(MessageFilesVO filesVO) throws Exception{
		return messageDAO.getFileDetail(filesVO);
	}

	public void readReceiveNotification(MessageVO messageVO, String userId) throws Exception{
		notificationService.checkMessageNotification(messageVO, userId);
	}
}
