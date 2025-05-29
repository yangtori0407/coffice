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
	private Integer checkStatus;
	private Integer deleteSend;
	private Integer deleteReceiver;
	
	private List<MessageFilesVO> files;
}
