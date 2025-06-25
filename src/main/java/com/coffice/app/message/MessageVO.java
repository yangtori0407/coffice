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
	
	private List<MessageFilesVO> files;
	
	//private List<MessageCheckVO> receiveUsers;
	
	private int messageCheckNum;
	private boolean checkStatus;
	private int messageStatus;
	private String userId;
	private boolean senderDelete;
	private boolean receiverDelete;
	
	private String name;
	private String deptName;
	
	
	
	public String getFormatted() {
		if(getSendDate() != null) {
			
			LocalDateTime localDateTime = this.getSendDate().toLocalDateTime();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
			return localDateTime.format(formatter);
		}
		return "";
	}
}
