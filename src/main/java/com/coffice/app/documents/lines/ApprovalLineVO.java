package com.coffice.app.documents.lines;

import java.sql.Timestamp;

import com.coffice.app.signs.SignVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApprovalLineVO {
	
	
	private Long aLineId;
	private Long documentId;
	private String userId;
	private String userName;
	private String userPosition;
	private Long stepOrder;
	private String status;
	private Timestamp handlingTime;
	private Long signId;
	private String rejectReason;
	
	private SignVO signVO;
	

}
