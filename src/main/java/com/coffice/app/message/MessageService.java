package com.coffice.app.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class MessageService {

	@Autowired
	private MessageDAO messageDAO;

	private final JavaMailSender messageMailSender;

	public MessageService(JavaMailSender messageMailSender) {
		this.messageMailSender = messageMailSender;
	}

	public int sendMessage(MessageVO messageVO, String[] receivers, String userId, String string) throws Exception {
		messageVO.setSender(userId);
		int result = messageDAO.add(messageVO);

		for(String s : receivers) {
			if(s.contains("@")) {
				
			}
		}

		sendMessageMail(messageVO, "heyang236@gmail.com");

		return result;
	}

	private void sendMessageMail(MessageVO messageVO, String email) throws Exception {
		MimeMessage mimeMessage = messageMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

		helper.setTo(messageVO.getReceiver());
		helper.setSubject(messageVO.getMessageTitle());
		helper.setText(messageVO.getMessageContents(), true);
		helper.setFrom(email, messageVO.getSender());

		helper.setReplyTo(email);

		messageMailSender.send(mimeMessage);
	}
}
