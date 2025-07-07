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
	
	//DB에서 조회시 담을 때만 사용 DB에는 없는 컬럼명
	private Integer alarmStatus;
	private Integer chatAmount;
}
