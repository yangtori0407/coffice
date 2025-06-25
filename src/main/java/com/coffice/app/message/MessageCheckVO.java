package com.coffice.app.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MessageCheckVO {
	
	private int messageCheckNum;
	private int messageNum;
	private boolean checkStatus;
	private int messageStatus;
	private String userId;
	private boolean senderDelete;
	private boolean receiverDelete;
	
	private String name;
	private String deptName;
}
