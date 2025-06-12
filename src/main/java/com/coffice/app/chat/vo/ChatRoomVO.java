package com.coffice.app.chat.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ChatRoomVO {
	private String chatRoomNum;
	private	String chatRoomName;
	private Date chatRoomDate;
	private String chatRoomCreator;
	
}
