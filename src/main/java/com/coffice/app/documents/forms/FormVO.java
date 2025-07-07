package com.coffice.app.documents.forms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FormVO {
	
	
	private Long formId;
	private String name;
	private String formFrame;
	private String grade1;
	private String grade2;
	private String grade3;
	private String grade4;
	private Long stepCount;

}
