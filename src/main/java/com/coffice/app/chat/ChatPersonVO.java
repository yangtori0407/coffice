package com.coffice.app.chat;

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
