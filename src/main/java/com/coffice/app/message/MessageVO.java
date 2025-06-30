package com.coffice.app.message;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MessageVO {
	private Long messageNum;
	private String messageContents;
	private String messageTitle;
	private Timestamp sendDate;
	private String sender;
	private boolean senderDelete;
	
	private String senderDept;
	private String senderName;
	private String email;
	
	private List<MessageFilesVO> files;
	
	//내가 이메일을 보낸 사람들
	private List<MessageCheckVO> receiveUsers;
	//내가 받은 이메일 상태
	private MessageCheckVO receiveMessage;
	
	
	
	public String getFormatted() {
		if(getSendDate() != null) {
			
			LocalDateTime localDateTime = this.getSendDate().toLocalDateTime();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
			return localDateTime.format(formatter);
		}
		return "";
	}
}
