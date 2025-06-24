package com.coffice.app.message;

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
	private String receiver;
	private String sender;
	private boolean checkStatus;
	private boolean deleteSend;
	private boolean deleteReceiver;
	
	private List<MessageFilesVO> files;
}
