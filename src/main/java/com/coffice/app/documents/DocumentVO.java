package com.coffice.app.documents;

import java.sql.Timestamp;
import java.util.List;

import com.coffice.app.documents.attachments.AttachmentVO;
import com.coffice.app.documents.forms.FormVO;
import com.coffice.app.documents.lines.ApprovalLineVO;
import com.coffice.app.documents.lines.ReferenceLineVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class DocumentVO {
	
	// 문서 정보 컬럼
	private Long documentId;	
	private String title;
	private String content;
	private Long currentStep;
	private String status;
		
	// 양식 컬럼
	private Long formId;
	private String formName;	
	
	// 작성자 컬럼
	private String writerId;
	private String writerName;
	private String writerPosition;
	private String writerDept;
	private Timestamp writerTime;
	
	// 수정자 컬럼	
	private String modifierId;
	private String modifierName;
	private String modifierPosition;
	private String modifierDept;
	private Timestamp modifierTime;
	
	// 자식 테이블들
	private List<AttachmentVO> attachmentVOs;
	private List<ApprovalLineVO> approvalLineVOs;
	private List<ReferenceLineVO> referenceLineVOs;
	

}
