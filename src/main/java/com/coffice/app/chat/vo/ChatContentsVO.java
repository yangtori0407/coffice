package com.coffice.app.chat.vo;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatContentsVO {
	
	private Long chatNum;
	private String chatRoomNum;
	private String chatContents;
	private Timestamp sendDate;
	private String sender;
	//보내는 사람 이름
	private String name;
	private boolean flag;
	private String fileNum;
	
	public String getFormatted() {
		if(getSendDate() != null) {
			
			LocalDateTime localDateTime = this.getSendDate().toLocalDateTime();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
			return localDateTime.format(formatter);
		}
		return "";
	}
	
}
