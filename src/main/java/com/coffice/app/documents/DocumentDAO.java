package com.coffice.app.documents;

import java.util.List;

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
	
	public List<DocumentVO> getList(Pager pager)throws Exception;
	
	public DocumentVO getDetail(DocumentVO documentVO)throws Exception;
	
	public int add(DocumentVO documentVO) throws Exception;
	
	public int addApprovalLine(ApprovalLineVO approvalLineVO) throws Exception;
	
	public int addReferenceLine(ReferenceLineVO referenceLineVO) throws Exception;
	
	public int addFile(AttachmentVO attachmentVO) throws Exception;
	
	public AttachmentVO getFileDetail(AttachmentVO attachmentVO)throws Exception;

}
