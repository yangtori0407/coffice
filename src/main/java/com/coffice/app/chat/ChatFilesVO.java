package com.coffice.app.chat;

import com.coffice.app.files.FileVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatFilesVO extends FileVO{
	
	private Long fileNum;
	private Long chatNum;
}
