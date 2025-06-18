package com.coffice.app.documents;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.documents.attachments.AttachmentVO;
import com.coffice.app.documents.forms.FormVO;
import com.coffice.app.documents.lines.ApprovalLineVO;
import com.coffice.app.documents.lines.ReferenceLineVO;
import com.coffice.app.files.FileManager;
import com.coffice.app.page.Pager;
import com.coffice.app.signs.SignVO;
import com.coffice.app.users.UserVO;

import ch.qos.logback.core.net.SyslogOutputStream;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class DocumentService {
	
	
	@Autowired
	private DocumentDAO documentDAO;
	
	@Autowired
	private FileManager fileManager;
	
	@Value("${app.files.base}")
	private String path;
	
	
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
	public List<FormVO> getFormsIdName() throws Exception {
				
		List<FormVO> list = documentDAO.getFormsIdName(); 
		
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
		
		// DAO로 보낼 map객체 및 조회 값 받아올 list 생성, map을 쓰는 이유는 pager도 보내야해서 그렇다. 여러개의 클래스를 mapper로 전달하기 위해
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
		
		case "handled" : // 문서 중 해당 문서의 결재선이 접속자이고, currentStep이 stepOrder보다 큰 (접속자의 처리가 된) 문서만 가져온다
			list = documentDAO.getListHandled(map);
			
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
		
		if(approvalLineVOs != null) {
			for(ApprovalLineVO approver : approvalLineVOs) {				
				
				// 결재선에서 sineId를 하나씩 꺼내와서 SIGNS의 상세 정보를 조회해온다
				SignVO signVO = new SignVO();
				signVO.setFileNum(approver.getSignId());
				signVO = documentDAO.getSignDetail(signVO);				
				 
				approver.setSignVO(signVO);
				
			}
			
		}		
		
		DocumentVO resultVO = documentDAO.getDetail(documentVO);
		resultVO.setAttachmentVOs(attachmentVOs);
		resultVO.setApprovalLineVOs(approvalLineVOs);
		resultVO.setReferenceLineVOs(referenceLineVOs);
		
		return resultVO;
	}
	
	
	//
	public int add(DocumentVO documentVO, List<ApprovalLineVO> approverList, List<ReferenceLineVO> referrerList,
			MultipartFile [] multipartFiles, HttpSession session) throws Exception {
		
		// documentVO에 데이터들 넣기
		documentVO.setWriterTime(Timestamp.valueOf(LocalDateTime.now()));
		documentVO.setCurrentStep(1L);
		documentVO.setStatus("진행중");
		
		// formName, stepCount는 문서 작성 시점의 폼 관련 값을 고정할 필요가 없으므로 문서 조회할 때 가져오겠다
		 
		
		
		
		
		// 결재선 데이터 넣기 (userId, name, position 들어가 있는 상태)		
		Long step = 1L;

		for (ApprovalLineVO vo : approverList) {			
			vo.setStepOrder(step++);	System.out.println("step : " + step);
			vo.setStatus("결재대기");			
		}		

		
		
		System.out.println("docu writerName은 : " + documentVO.getWriterName());
		System.out.println("docu Name은 : " + documentVO.getWriterName());
		System.out.println("docu Position은 : " + documentVO.getWriterPosition());
		System.out.println("docu Dept은 : " + documentVO.getWriterDept());
		// 문서 DB추가
		int result = documentDAO.add(documentVO);
		// insert 실행하면서 생긴 documentId (PK) **
		// Mapper에서 Mybatis의 useGeneratedKeys="true" keyProperty="documentId"를 써서 가져옴
		
		
		// 결재선 DB추가
		if (approverList != null) {
			for(ApprovalLineVO vo : approverList) {
				
				vo.setDocumentId(documentVO.getDocumentId());
				// documentId, userId, userName, userPosition, stepOrder, status 있는 상태
				
				result = documentDAO.addApprovalLine(vo);
				
			}
		}
		
		
		// 참조선 DB추가
		if (referrerList != null) {
			for(ReferenceLineVO vo : referrerList) {
				
				vo.setDocumentId(documentVO.getDocumentId());
				// documentId, userId, userName, userPosition있는 상태
				
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
	
	
	// 직인 디테일 조회
	/*
	 * public String getSignPath(ApprovalLineVO approvalLineVO) throws Exception {
	 * 
	 * String result = "없는 직인 입니다";
	 * 
	 * if (approvalLineVO.getSignId() == 1) { result = "/images/coffice.png";
	 * //테스트용으로 이미지 경로 보내본다, 원래는 서버에 저장된 실제 직인 파일의 경로와 이름이 필요하다
	 * 
	 * } else if (approvalLineVO.getSignId() == 2) { result = "/images/3.png";
	 * //테스트용으로 이미지 경로 보내본다, 원래는 서버에 저장된 실제 직인 파일의 경로와 이름이 필요하다 }
	 * 
	 * 
	 * return result; }
	 */
	
	
	//
	public int addSign(HttpSession session, MultipartFile[] attaches) throws Exception {
		
		UserVO userVO = (UserVO)session.getAttribute("userVO");
		SignVO signVO = new SignVO();
		signVO.setUserId(userVO.getUserId());
		
		int result = 0;
		
		for (MultipartFile attach : attaches) {
			if(attach.isEmpty()) {
				continue;
			}
			
			String fileName = this.fileSave(attach);
			signVO.setSaveName(fileName);
			signVO.setOriginName(attach.getOriginalFilename());
			
			result += documentDAO.addSign(signVO);
		}
		
		return result;
	}
	
	
	//
	public String fileSave(MultipartFile attach) throws Exception{
		
		String fileName = fileManager.fileSave(path.concat("signs"), attach);
		
		
		return fileName;
	}
	
	
	
	// 직인 목록 조회
	public List<SignVO> getSignList (HttpSession session) throws Exception {
		
		UserVO userVO = (UserVO)session.getAttribute("userVO");
		
		List<SignVO> list = documentDAO.getSignList(userVO);
		
		if(list != null) {
			// 가져온 list에서 originName의 확장자를 제거해준다.
			for(SignVO vo : list) {
				int index = vo.getOriginName().lastIndexOf(".");
				String nameOnly = vo.getOriginName().substring(0, index);
				
				vo.setOriginName(nameOnly);
			}
			
		}
				
		return list;
	}
	
	
	// 결재 처리 (결재선 업데이트)
	public int updateApprovalProceed(ApprovalLineVO approvalLineVO, HttpSession session) throws Exception {
		
		int result = 0;
		
		// approvalLineVO : documentId, signId, userId(접속자) 가져온 상태
		approvalLineVO.setStatus("승인");
		approvalLineVO.setHandlingTime(Timestamp.valueOf(LocalDateTime.now()));
		
		// 결재자 정보 업데이트
		result = documentDAO.updateApprovalProceed(approvalLineVO);
		
		// 결재자 정보 가져오기 (
		approvalLineVO = documentDAO.getApprovalDetail(approvalLineVO);
		
		
		//------------------------------------------------------------
		UserVO user = (UserVO)session.getAttribute("userVO"); // 접속자의 정보를 가져왔다 
		user = documentDAO.getUserDetail(user); // 접속자의 userId를 이용해 DB로부터 user에 데이터를 담아온다
		
		//
		DocumentVO documentVO = new DocumentVO(); 
		documentVO.setDocumentId(approvalLineVO.getDocumentId()); // documentId 전달할 그릇 생성
		
		documentVO = documentDAO.getDetail(documentVO);  // 그릇 재활용하여 업데이트 대상인 문서 정보 담아오기 (결재 전 문서 정보)
		
		
		// 결재 관련 데이터를 변경해준다. 수정자 변수에 접속자 정보(수정하는 사람)도 넣어준다.		
		// 중간 결재자면 status 유지 및 step은 +1
		// 마지막 결재자면 status는 '결재완료'로 변경, step은 유지
		System.out.println("docuVO stepCount : " + documentVO.getStepCount());
		System.out.println("appVO StepOrder : " + approvalLineVO.getStepOrder());
		
		if(documentVO.getStepCount() == approvalLineVO.getStepOrder()) {
			documentVO.setStatus("결재완료");			
		} 		
		documentVO.setCurrentStep(documentVO.getCurrentStep() + 1); // 결재할 때마다 +1
		
		documentVO.setModifierId(user.getUserId());
		documentVO.setModifierName(user.getName());
		documentVO.setModifierPosition(user.getPosition());
		documentVO.setModifierDept(user.getDeptName());
		documentVO.setModifierTime(approvalLineVO.getHandlingTime()); // 가장 최근 결재자의 처리 시간을 넣는다.
		
		
		// 문서 정보 업데이트
		result = documentDAO.updateDocumentProceed(documentVO); 
		
		
		
		return result;
	}
	
	
	
	
	
	
	
	

}
