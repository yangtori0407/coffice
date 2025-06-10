package com.coffice.app.documents;

import java.sql.Timestamp;
import java.util.List;

import com.coffice.app.documents.attachments.AttachmentVO;
import com.coffice.app.documents.lines.ApprovalLineVO;
import com.coffice.app.documents.lines.ReferenceLineVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class DocumentVO {
	
	
	private Long documentId;
	private Long formId;
	private String title;
	private String content;
	private String userId;
	private Timestamp time;
	private Long currentStep;
	private String status;
	
	private List<AttachmentVO> attachmentVOs;
	private List<ApprovalLineVO> approvalLineVOs;
	private List<ReferenceLineVO> referenceLineVOs;
	
	
	// 화면 출력용 조인 컬럼
	private String userName;
	private String userPosition;
	private String formName;
	private Long stepCount;

}
