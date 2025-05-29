package com.coffice.app.message;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageVO {
	private Long messageNum;
	private String messageContents;
	private String receiver;
	private String sender;
	private boolean checkStatus;
	private boolean deleteSend;
	private boolean deleteReceiver;
	
	private List<MessageFilesVO> files;
}
