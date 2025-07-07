package com.coffice.app.chat.vo;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ChatPersonVO {
	
	private String chatRoomNum;
	private String userId;
	private Timestamp joinedAt;
	private Timestamp lastReadAt;
	//기본 값 1 => 알림 받기 0 => 알림 끄기
	private Integer alarmStatus;
}
