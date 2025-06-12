package com.coffice.app.chat.vo;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ChatPersonVO {
	
	private Long chatRoomNum;
	private String userId;
	private Timestamp joinedAt;
	private Timestamp lastReadAt;
}
