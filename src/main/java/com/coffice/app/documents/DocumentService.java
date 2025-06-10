package com.coffice.app.documents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
	public List<DocumentVO> getList(Pager pager, HttpServletRequest request, HttpSession session) throws Exception {
		
		// 페이지 관련 값 준비
		pager.make();
		pager.makeNum(documentDAO.getTotalCount(pager));
		
		// request에서 uri 가져오기 
		String uri = request.getRequestURI();
		
		// 들어온 url 마지막 키워드만 파싱
		String[] parts = uri.split("/");
		String url = parts[parts.length-1];
		
		// DAO로 보낼 map객체 및 조회 값 받아올 list 생성
		Map<String, Object> map = new HashMap<>();
		
		// 접속 중인 session의 유저 정보 가져오기
		UserVO user = (UserVO)session.getAttribute("userVO");
		map.put("userId", user.getUserId());
		
		List<DocumentVO> list = null;
		
		// 파싱한 키워드에 따라 조건 분기 생성
		switch(url) {
		case "online" : // 문서 중 작성자가 접속자인 문서만 가져온다, status는 "임시"를 제외한 "결재 중", "반려", "결재 완료"만 해당한다 
			list = documentDAO.getListLine(map);
			
			break;
			
		case "onwaiting" : // 문서 중 해당 문서의 결재선이 접속자이고, stepOrder와 문서의 currentStep이 일치하는 문서만 가져온다
			// approvalVO
			list = documentDAO.getListWaiting(map);
			
			break;
			
		case "onreference" : // 문서 중 해당 문서의 참조선이 접속자인 문서만 가져온다
			// referVO
			// get document where userId 맞는 참조선의 documentId도 맞는 문서
			list = documentDAO.getListReference(map);
			
			break;
			
		case "ontemporary" : // 문서 중 작성자가 접속자이고, status가 "임시"인 문서만 가져온다
			list = documentDAO.getListTemporary(map);
			
			break;
		
		default :
			list = documentDAO.getListLine(map);
			
		}
		
		return list;
	}
	
	
	//
	public DocumentVO getDetail(DocumentVO documentVO) throws Exception {
					
		List<AttachmentVO> attachmentVOs = documentDAO.getChildrenFiles(documentVO);
		List<ApprovalLineVO> approvalLineVOs = documentDAO.getChildrenApprovers(documentVO);
		List<ReferenceLineVO> referenceLineVOs = documentDAO.getChildrenReferrers(documentVO);
		
		DocumentVO resultVO = documentDAO.getDetail(documentVO);
		resultVO.setAttachmentVOs(attachmentVOs);
		resultVO.setApprovalLineVOs(approvalLineVOs);
		resultVO.setReferenceLineVOs(referenceLineVOs);
		
		return resultVO;
	}
	
	
	//
	public int add(DocumentVO documentVO, List<ApprovalLineVO> approvalLineVOs, List<ReferenceLineVO> referenceLineVOs,
			MultipartFile [] multipartFiles) throws Exception {
		
		// 문서 DB추가
		int result = documentDAO.add(documentVO); 
		// insert 실행하면서 생긴 documentId (PK)
		// Mapper에서 Mybatis의 useGeneratedKeys="true" keyProperty="documentId"를 써서 가져옴
		
		// 결재선 DB추가
		if (approvalLineVOs != null) {
			for(ApprovalLineVO vo : approvalLineVOs) {
				
				vo.setDocumentId(documentVO.getDocumentId());
				// documentId, userId, stepOrder, status 있는 상태
				
				result = documentDAO.addApprovalLine(vo);
				
			}
		}
		
		// 참조선 DB추가
		if (referenceLineVOs != null) {
			for(ReferenceLineVO vo : referenceLineVOs) {
				
				vo.setDocumentId(documentVO.getDocumentId());
				// documentId, userId 있는 상태
				
				result = documentDAO.addReferenceLine(vo);
				
			}
		}
		
		
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
