package com.coffice.app.chat.vo;

import com.coffice.app.files.FileVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ChatFilesVO extends FileVO{
	
	private Long chatNum;
}
