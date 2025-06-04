package com.coffice.app.documents;

import java.sql.Date;
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
	private Date time;
	private Long currentStep;
	private String status;
	
	private List<AttachmentVO> attachmentVOs;
	private List<ApprovalLineVO> approvalLineVOs;
	private List<ReferenceLineVO> referenceLineVOs;

}
