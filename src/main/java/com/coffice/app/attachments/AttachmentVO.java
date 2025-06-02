package com.coffice.app.attachments;

import com.coffice.app.files.FileVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttachmentVO extends FileVO{
	
	private Long document_id;

}
