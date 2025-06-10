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
	
	// 화면 출력용 조인 컬럼
	private String userName;
	private String userPosition;

}
