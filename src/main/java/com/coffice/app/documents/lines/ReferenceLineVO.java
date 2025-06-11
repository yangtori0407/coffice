package com.coffice.app.documents.lines;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReferenceLineVO {
	
	
	private Long rLineId;
	private Long documentId;
	private String userId;
	private String userName;
	private String userPosition;
	
	
	
}
