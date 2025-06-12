package com.coffice.app.chat.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ChatAddVO {
	
	private String chatRoomNum;
	private String name;
	private List<String> users;
}
