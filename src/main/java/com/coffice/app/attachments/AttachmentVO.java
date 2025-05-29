package com.coffice.app.attachments;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttachmentVO {
	
	
	private Long attachment_id;
	private Long document_id;
	private String origin_name;
	private String save_name;

}
