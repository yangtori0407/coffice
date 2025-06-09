package com.coffice.app.documents;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.documents.attachments.AttachmentVO;
import com.coffice.app.documents.forms.FormVO;
import com.coffice.app.documents.lines.ApprovalLineVO;
import com.coffice.app.documents.lines.ReferenceLineVO;
import com.coffice.app.files.FileManager;
import com.coffice.app.page.Pager;
import com.coffice.app.users.UserVO;

@Service
public class DocumentService {
	
	
	@Autowired
	private DocumentDAO documentDAO;
	
	@Autowired
	private FileManager fileManager;
	
	
	//
	public FormVO formDetail(FormVO formVO) throws Exception {
		
		return documentDAO.formDetail(formVO);
	}
	
	
	//
	public List<UserVO> getUsers() throws Exception {
				
		List<UserVO> list = documentDAO.getUsers();
		
		return list;
	}
	
	
	//
	public List<FormVO> getForms() throws Exception {
				
		List<FormVO> list = documentDAO.getForms(); 
		
		return list;
	}
	
	
	//
	public List<DocumentVO> getList(Pager pager) throws Exception {
		
		//pager.make(documentDAO.getTotalCount(pager));
		//pager.makeNum();
		List<DocumentVO> list = documentDAO.getList(pager); 
		
		return list;
	}
	
	
	//
	public DocumentVO getDetail(DocumentVO documentVO) throws Exception {
		
				
		return documentDAO.getDetail(documentVO);
	}
	
	
	//
	public int add(DocumentVO documentVO, List<ApprovalLineVO> approvalLineVOs, List<ReferenceLineVO> referenceLineVOs,
			MultipartFile [] multipartFiles) throws Exception {
		
		// 문서 DB추가
		int result = documentDAO.add(documentVO); // 이번에 생긴 documentId를 가져와서 넣어줘야 하는데?
		
		// 결재선 DB추가
		if (approvalLineVOs != null) {
			for(ApprovalLineVO vo : approvalLineVOs) {
				
				vo.setDocumentId(documentVO.getDocumentId());
				// userId, stepOrder, status 있는 상태
				
				result = documentDAO.addApprovalLine(vo);
				
			}
		}
		
		// 참조선 DB추가		
		
		
		// 파일 세이브
		if (multipartFiles != null) {
			for(MultipartFile f : multipartFiles) {
				
				if(f.isEmpty()) {
					continue;
				}
				
				String fileName = fileManager.fileSave("패스 넣어야함", f);
				
				// 파일명 DB추가
				AttachmentVO vo = new AttachmentVO();
				vo.setDocumentId(documentVO.getDocumentId());
				vo.setOriginName(f.getOriginalFilename());
				vo.setSaveName(fileName);
				
				result = documentDAO.addFile(vo);
				
			}
			
			
		}
		
		
		return result;
	}
	
	
	//
	public AttachmentVO getFileDetail(AttachmentVO attachmentVO) throws Exception {
		
		
		return documentDAO.getFileDetail(attachmentVO);
	}
	
	
	
	
	

}
