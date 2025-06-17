package com.coffice.app.documents;

import java.util.List;
import java.util.Map;


import org.apache.ibatis.annotations.Mapper;

import com.coffice.app.documents.attachments.AttachmentVO;
import com.coffice.app.documents.forms.FormVO;
import com.coffice.app.documents.lines.ApprovalLineVO;
import com.coffice.app.documents.lines.ReferenceLineVO;
import com.coffice.app.page.Pager;
import com.coffice.app.signs.SignVO;
import com.coffice.app.users.UserVO;


@Mapper
public interface DocumentDAO {
	
	// 세팅 시 필요 데이터 조회
	public FormVO formDetail(FormVO formVO) throws Exception;
	
	public List<UserVO> getUsers() throws Exception;
	
	public List<FormVO> getForms() throws Exception;
	
	public Long getTotalCount(Pager pager)throws Exception;
	
	
	// 문서 목록 조회 ----------------------------------
	public List<DocumentVO> getListLine(Map<String, Object> map)throws Exception;
	
	public List<DocumentVO> getListHandled(Map<String, Object> map)throws Exception;
	
	public List<DocumentVO> getListWaiting(Map<String, Object> map)throws Exception;
	
	public List<DocumentVO> getListReference(Map<String, Object> map)throws Exception;	
	
	public List<DocumentVO> getListTemporary(Map<String, Object> map)throws Exception;
	
	
	// 결재 관련 ----------------------------------	
	public int updateDocumentProceed(DocumentVO documentVO) throws Exception;
		
	public int updateApprovalProceed(ApprovalLineVO approvalLineVO) throws Exception;	
	
	public List<SignVO> getSignList(UserVO userVO)throws Exception;
	
	public ApprovalLineVO getApprovalDetail(ApprovalLineVO approvalLineVO) throws Exception;
	
	public SignVO getSignDetail(SignVO signVO) throws Exception;
	
	public int addSign(SignVO signVO) throws Exception;
	
	
	// 유저 조회 ----------------------------------
	public UserVO getUserDetail(UserVO userVO)throws Exception;	
	
	
	// 문서 상세 조회 ----------------------------------
	public DocumentVO getDetail(DocumentVO documentVO)throws Exception;
	
	public List<AttachmentVO> getChildrenFiles(DocumentVO documentVO)throws Exception;
	
	public List<ApprovalLineVO> getChildrenApprovers(DocumentVO documentVO)throws Exception;
	
	public List<ReferenceLineVO> getChildrenReferrers(DocumentVO documentVO)throws Exception;
	
	
	// 문서 작성 ----------------------------------
	public int add(DocumentVO documentVO) throws Exception;
	
	public int addApprovalLine(ApprovalLineVO approvalLineVO) throws Exception;
	
	public int addReferenceLine(ReferenceLineVO referenceLineVO) throws Exception;
	
	public int addFile(AttachmentVO attachmentVO) throws Exception;
	
	public AttachmentVO getFileDetail(AttachmentVO attachmentVO)throws Exception;
	

}
