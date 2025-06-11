package com.coffice.app.documents;

import java.util.List;
import java.util.Map;


import org.apache.ibatis.annotations.Mapper;

import com.coffice.app.documents.attachments.AttachmentVO;
import com.coffice.app.documents.forms.FormVO;
import com.coffice.app.documents.lines.ApprovalLineVO;
import com.coffice.app.documents.lines.ReferenceLineVO;
import com.coffice.app.page.Pager;
import com.coffice.app.users.UserVO;


@Mapper
public interface DocumentDAO {
	
	public FormVO formDetail(FormVO formVO) throws Exception;
	
	public List<UserVO> getUsers() throws Exception;
	
	public List<FormVO> getForms() throws Exception;
	
	public Long getTotalCount(Pager pager)throws Exception;
	
	
	public List<DocumentVO> getListLine(Map<String, Object> map)throws Exception;
	
	public List<DocumentVO> getListReference(Map<String, Object> map)throws Exception;
	
	public List<DocumentVO> getListWaiting(Map<String, Object> map)throws Exception;
	
	public List<DocumentVO> getListTemporary(Map<String, Object> map)throws Exception;
	
	
	public DocumentVO getDetail(DocumentVO documentVO)throws Exception;
	
	public List<AttachmentVO> getChildrenFiles(DocumentVO documentVO)throws Exception;
	
	public List<ApprovalLineVO> getChildrenApprovers(DocumentVO documentVO)throws Exception;
	
	public List<ReferenceLineVO> getChildrenReferrers(DocumentVO documentVO)throws Exception;
	
	
	public int add(DocumentVO documentVO) throws Exception;
	
	public int addApprovalLine(ApprovalLineVO approvalLineVO) throws Exception;
	
	public int addReferenceLine(ReferenceLineVO referenceLineVO) throws Exception;
	
	public int addFile(AttachmentVO attachmentVO) throws Exception;
	
	public AttachmentVO getFileDetail(AttachmentVO attachmentVO)throws Exception;

}
