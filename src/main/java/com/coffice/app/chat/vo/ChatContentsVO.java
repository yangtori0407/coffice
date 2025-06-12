package com.coffice.app.chat.vo;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatContentsVO {
	
	private Long chatNum;
	private Long chatRoomNum;
	private String chatContents;
	private Timestamp sendDate;
	private String sender;
	
}
