package com.coffice.app.documents;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class DocumentVO {
	
	
	private Long document_id;
	private Long form_id;
	private String title;
	private String content;
	private String user_id;
	private Date time;
	private Long current_step;
	private String status;
	

}
