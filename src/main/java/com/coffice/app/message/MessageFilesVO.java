package com.coffice.app.message;

import com.coffice.app.files.FileVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MessageFilesVO extends FileVO{
	
	private Long fileNum;
	private Long messageNum;
}
