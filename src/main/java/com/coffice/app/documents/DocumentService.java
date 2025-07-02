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
import com.coffice.app.notification.NotificationService;
import com.coffice.app.page.Pager;
import com.coffice.app.signs.SignVO;
import com.coffice.app.users.UserVO;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DocumentService {
	
	
	@Autowired
	private DocumentDAO documentDAO;
	
	@Autowired
	private FileManager fileManager;
	
	@Autowired
	private NotificationService notificationService;
	
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
	public List<DocumentVO> getLastTemp(HttpSession session) throws Exception {
		
		DocumentVO documentVO = new DocumentVO();
		
		if(session.getAttribute("user") != null) {
			UserVO userVO = (UserVO)session.getAttribute("user");
			documentVO.setWriterId(userVO.getUserId());
			
		}
		
		return documentDAO.getLastTemp(documentVO);
	}
	
	
	//
	public List<DocumentVO> getlastHandled(HttpSession session) throws Exception {
			
		DocumentVO documentVO = new DocumentVO();
		
		if(session.getAttribute("user") != null) {
			UserVO userVO = (UserVO)session.getAttribute("user");
			documentVO.setWriterId(userVO.getUserId());
			
		}
		
		return documentDAO.getlastHandled(documentVO);
	}
	
	
	//
	public List<DocumentVO> getTodayReference(HttpSession session) throws Exception {
		
		DocumentVO documentVO = new DocumentVO();
		
		if(session.getAttribute("user") != null) {
			UserVO userVO = (UserVO)session.getAttribute("user");
			documentVO.setWriterId(userVO.getUserId());
			
		}
		
		return documentDAO.getTodayReference(documentVO);
	}
	
	
	//
	public List<DocumentVO> getTodayWaiting(HttpSession session) throws Exception {
		
		DocumentVO documentVO = new DocumentVO();
		
		if(session.getAttribute("user") != null) {
			UserVO userVO = (UserVO)session.getAttribute("user");
			documentVO.setWriterId(userVO.getUserId());
			
		}
		
		return documentDAO.getTodayWaiting(documentVO);
	}
	
	
	//
	public Map<String, Object> getList(Pager pager, HttpServletRequest request, HttpSession session) throws Exception {
		
				
		// request에서 uri 가져오기 
		String uri = request.getRequestURI();
		
		// 들어온 url 마지막 키워드만 파싱
		String[] parts = uri.split("/");
		String url = parts[parts.length-1];
		
		// DAO로 보낼 map객체 및 조회 값 받아올 list 생성, map을 쓰는 이유는 pager도 보내야해서 그렇다. 여러개의 클래스를 mapper로 전달하기 위해
		Map<String, Object> map = new HashMap<>();
		
		// 접속 중인 session의 유저 정보 가져오기
		UserVO user = (UserVO)session.getAttribute("user");
		map.put("userId", user.getUserId());
		//System.out.println("userId : " + map.get("userId"));
		map.put("search", pager.getSearch());
		//System.out.println("search : " + map.get("search"));
		map.put("kind", pager.getKind());
		//System.out.println("kind : " + map.get("kind"));
		
		List<DocumentVO> list = null;
		String kind = "";
		String docuKind = "";
		
		// 파싱한 키워드에 따라 조건 분기 생성
		switch(url) {
		case "online" : // 문서 중 작성자가 접속자인 문서만 가져온다, status는 "임시"를 제외한 "결재 중", "반려", "결재 완료"만 해당한다
			
			// 페이지 관련 값 준비
			pager.make();
			pager.makeNum(documentDAO.getTotalCountOnline(map));
			
			map.put("startNum", pager.getStartNum());
			//System.out.println("startNum : " + map.get("startNum"));
			map.put("page", pager.getPage());
			//System.out.println("page : " + map.get("page"));
			
			list = documentDAO.getListLine(map);			
			kind = "결재 > 기안 문서함";
			docuKind = "online";
			break;
		
		case "handled" : // 문서 중 해당 문서의 결재선이 접속자이고, currentStep이 stepOrder보다 큰 (접속자의 처리가 된) 문서만 가져온다
			
			// 페이지 관련 값 준비
			pager.make();
			pager.makeNum(documentDAO.getTotalCountHandled(map));
			
			map.put("startNum", pager.getStartNum());
			map.put("page", pager.getPage());
			
			list = documentDAO.getListHandled(map);
			kind = "결재 > 승인/반려 문서함";
			docuKind = "handled";
			break;
			
		case "onwaiting" : // 문서 중 해당 문서의 결재선이 접속자이고, stepOrder와 문서의 currentStep이 일치하는 문서만 가져온다
			// approvalVO
			
			pager.make();
			pager.makeNum(documentDAO.getTotalCountWaiting(map));
			
			map.put("startNum", pager.getStartNum());
			map.put("page", pager.getPage());
			
			list = documentDAO.getListWaiting(map);
			kind = "결재 > 결재 대기 문서함";
			docuKind = "onwaiting";
			break;
			
		case "onreference" : // 문서 중 해당 문서의 참조선이 접속자인 문서만 가져온다
			// referVO
			// get document where userId 맞는 참조선의 documentId도 맞는 문서
			
			pager.make();
			pager.makeNum(documentDAO.getTotalCountReference(map));
			
			map.put("startNum", pager.getStartNum());
			map.put("page", pager.getPage());
			
			list = documentDAO.getListReference(map);
			kind = "결재 > 참조 문서함";
			docuKind = "onreference";
			break;
			
		case "ontemporary" : // 문서 중 작성자가 접속자이고, status가 "임시저장"인 문서만 가져온다
			
			pager.make();
			pager.makeNum(documentDAO.getTotalCountTemporary(map));
			
			map.put("startNum", pager.getStartNum());
			map.put("page", pager.getPage());
			
			list = documentDAO.getListTemporary(map);
			kind = "결재 > 임시 저장 문서함";
			docuKind = "ontemporary";
			break;
		
		default :
			
			pager.make();
			pager.makeNum(documentDAO.getTotalCountOnline(map));
			
			map.put("startNum", pager.getStartNum());
			map.put("page", pager.getPage());
			
			list = documentDAO.getListLine(map);
			kind = "결재 > 기안 문서함";
			docuKind = "online";
		}
		
		// 각기 다른 성격의 document가 담긴 list가 만들어졌다.
		// document마다 결재선이 있으므로 반복을 돌면서 결재선을 조회해온다. 조회한 결재선을 document에 각각 집어넣는다.
		for(DocumentVO vo : list) {
			List<ApprovalLineVO> childrenApprovers = documentDAO.getChildrenApprovers(vo);
			vo.setApprovalLineVOs(childrenApprovers);
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("list", list);
		result.put("kind", kind);
		result.put("docuKind", docuKind);
		
		//결재 알림 읽음 처리
		//log.info("{}",docuKind);
		notificationService.checkDocumentNotification(docuKind, user.getUserId());
		
		return result;
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
			MultipartFile [] multipartFiles) throws Exception {
		
		// documentVO에 데이터들 넣기
		documentVO.setWriterTime(Timestamp.valueOf(LocalDateTime.now()));
		documentVO.setCurrentStep(1L);
		// 임시저장 기능 추가로 (documentVO의 status에 임의 값 세팅이 아닌 '임시저장' 또는 '진행중' 데이터를 받아오는 것으로 코드 변경
		//documentVO.setStatus("진행중");
		//System.out.println("status : " + documentVO.getStatus());
		
		// formName, stepCount는 문서 작성 시점의 폼 관련 값을 고정할 필요가 없으므로 문서 조회할 때 가져오겠다
		
		
		// 결재선 데이터 넣기 (userId, name, position 들어가 있는 상태)		
		Long step = 1L;

		for (ApprovalLineVO vo : approverList) {			
			vo.setStepOrder(step++);	//System.out.println("step : " + step);
			vo.setStatus("결재대기");			
		}		
		
		
		//System.out.println("docu writerName은 : " + documentVO.getWriterName());		
		//System.out.println("docu Position은 : " + documentVO.getWriterPosition());
		//System.out.println("docu Dept은 : " + documentVO.getWriterDept());
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
		result = 0;
		String folderName = "docuFiles";
		
		if (multipartFiles != null) {
			for(MultipartFile f : multipartFiles) {
				//System.out.println("f.oriName : " + f.getOriginalFilename());
				
				if(f.isEmpty()) {
					continue;
				}
				
				String fileName = this.fileSave(f, folderName);
				
				// 파일명 DB추가
				AttachmentVO fileVO = new AttachmentVO();
				fileVO.setDocumentId(documentVO.getDocumentId());
				fileVO.setOriginName(f.getOriginalFilename());
				fileVO.setSaveName(fileName);
				
				result += documentDAO.addFile(fileVO);
				
			}
			
			
		}
		
		
		return result;
	}
	
	
	//
	public int deleteTemp(DocumentVO documentVO) throws Exception {
		
		return documentDAO.deleteTemp(documentVO);
	}
	
	
	//
	public int updateOnlyStatus(DocumentVO documentVO) throws Exception {
		
		documentVO.setStatus("임시저장");
		
		return documentDAO.updateOnlyStatus(documentVO);
	}
	
	
	
	//
	public int updateTemp(DocumentVO documentVO, List<ApprovalLineVO> approverList, List<ReferenceLineVO> referrerList,
			MultipartFile [] multipartFiles, Long[] exists) throws Exception {
		
		// documentVO에 새로 작성 시간 넣기
		documentVO.setWriterTime(Timestamp.valueOf(LocalDateTime.now()));
		
		
		//System.out.println("docu writerName은 : " + documentVO.getWriterName());
		//System.out.println("docu Position은 : " + documentVO.getWriterPosition());
		//System.out.println("docu Dept은 : " + documentVO.getWriterDept());
		// 문서 기본 정보 및 제목, 내용 업데이트
		int result = documentDAO.updateTemp(documentVO);
		
		// 기존 결재선 및 참조선 제거 해주고
		result = documentDAO.deleteApprovalLine(documentVO);
		result = documentDAO.deleteReferenceLine(documentVO);		
		
		
		// 새 결재선 데이터 넣기 (userId, name, position 들어가 있는 상태)		
		Long step = 1L;

		for (ApprovalLineVO vo : approverList) {			
			vo.setStepOrder(step++);	//System.out.println("step : " + step);
			vo.setStatus("결재대기");			
		}
		
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
		
		
		String folderName = "docuFiles";
		result = 0;
		
		// 기존 파일 목록에서 없는 것 삭제하기
		//System.out.println("file docuId : " + documentVO.getDocumentId());

		// 1. 기존 첨부파일 목록 조회
		List<AttachmentVO> beforeList = documentDAO.getChildrenFiles(documentVO);

		// 2. 사용자로부터 전달된 '유지할 파일번호' 리스트 준비
		List<Long> keepFileNums = new ArrayList<>();
		if (exists != null) {
		    keepFileNums = new ArrayList<>(List.of(exists));
		}

		// 3. 삭제 대상 파일 처리
		for (AttachmentVO file : beforeList) {
		    
			// exists가 null이거나 file의 fileNum이 keepFileNums와 비교해서 겹치는게 없으면 shouldDelete에 true를 준다 >> 삭제처리
		    boolean shouldDelete = exists == null || !keepFileNums.contains(file.getFileNum());

		    if (shouldDelete) {
		        //System.out.println("삭제 대상 파일번호: " + file.getFileNum());

		        // 3-1. DB에서 삭제
		        result += documentDAO.deleteAttachment(file);

		        // 3-2. 실제 파일 삭제
		        String fullPath = path.concat(folderName);
		        fileManager.fileDelete(fullPath, file.getSaveName());
		    }
		}

		
		
		// 신규 파일 세이브
		if (multipartFiles != null) {
			for(MultipartFile f : multipartFiles) {
				//System.out.println("f.oriName : " + f.getOriginalFilename());
				
				if(f.isEmpty()) {
					continue;
				}
				
				String fileName = this.fileSave(f, folderName);
				
				
				// 파일명 DB추가
				AttachmentVO fileVO = new AttachmentVO();
				fileVO.setDocumentId(documentVO.getDocumentId());
				//System.out.println("getDocumentId : " + documentVO.getDocumentId());
				
				fileVO.setOriginName(f.getOriginalFilename());
				//System.out.println("f.getOriginalFilename() : " + f.getOriginalFilename());
				
				fileVO.setSaveName(fileName);
				//System.out.println("fileName : " + fileName);
				
				result += documentDAO.addFile(fileVO);
				
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
		
		UserVO userVO = (UserVO)session.getAttribute("user");
		SignVO signVO = new SignVO();
		signVO.setUserId(userVO.getUserId());
		
		int result = 0;
		String folderName = "signs";
		
		for (MultipartFile attach : attaches) {
			if(attach.isEmpty()) {
				continue;
			}
			
			String fileName = this.fileSave(attach, folderName);
			signVO.setSaveName(fileName);
			signVO.setOriginName(attach.getOriginalFilename());
			
			result += documentDAO.addSign(signVO);
		}
		
		return result;
	}
	
	
	//
	public String fileSave(MultipartFile attach, String folderName) throws Exception{
		
		String fileName = fileManager.fileSave(path.concat(folderName), attach);
		
		
		return fileName;
	}
	
	
	
	// 직인 목록 조회
	public List<SignVO> getSignList (HttpSession session) throws Exception {
		
		UserVO userVO = (UserVO)session.getAttribute("user");
		
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
		UserVO user = (UserVO)session.getAttribute("user"); // 접속자의 정보를 가져왔다 
		user = documentDAO.getUserDetail(user); // 접속자의 userId를 이용해 DB로부터 user에 데이터를 담아온다
		
		//
		DocumentVO documentVO = new DocumentVO(); 
		documentVO.setDocumentId(approvalLineVO.getDocumentId()); // documentId 전달할 그릇 생성
		
		documentVO = documentDAO.getDetail(documentVO);  // 그릇 재활용하여 업데이트 대상인 문서 정보 담아오기 (결재 전 문서 정보)
		
		
		// documentId 이용해서 그 문서의 결재선들 가져오기 (결재선 갯수 구하려고)
		List<ApprovalLineVO> childrenApprovers = documentDAO.getChildrenApprovers(documentVO);
		
		// 결재 관련 데이터를 변경해준다. 수정자 변수에 접속자 정보(수정하는 사람)도 넣어준다.		
		// 중간 결재자면 status 유지 및 문서의 step은 +1
		// 마지막 결재자면 status는 '결재완료'로 변경
		//System.out.println("childrenApprovers size : " + childrenApprovers.size());
		//System.out.println("appVO StepOrder : " + approvalLineVO.getStepOrder());
		
		if(childrenApprovers.size() == approvalLineVO.getStepOrder()) {
			documentVO.setStatus("결재완료");
			// 문서가 "결재완료" 상태가 되면 작성자한테 결재완료 알림을 보낸다 
			notificationService.sendApprovalLine(documentVO, documentVO.getWriterId(), 0);
			
		} 		
		documentVO.setCurrentStep(documentVO.getCurrentStep() + 1); // 결재할 때마다 문서 스텝 +1
		
		// 문서가 아직 진행 중이라면 다음 결재권자에게 알림을 보낸다, 완료 상태라면 어차피 문서 step이 더 크므로 받을 사람이 없다.
		for(ApprovalLineVO approver : childrenApprovers) {
			if(documentVO.getCurrentStep() == approver.getStepOrder()) {
				notificationService.sendApprovalLine(documentVO, approver.getUserId(), 1);
			}
		}
		
		
		
		documentVO.setModifierId(user.getUserId());
		documentVO.setModifierName(user.getName());
		documentVO.setModifierPosition(user.getPosition());
		documentVO.setModifierDept(user.getDeptName());
		documentVO.setModifierTime(approvalLineVO.getHandlingTime()); // 가장 최근 결재자의 처리 시간을 넣는다.
		
		
		// 문서 정보 업데이트
		result = documentDAO.updateDocumentProceed(documentVO); 
		
		
		
		return result;
	}
	
	
	
	// 반려 처리 (결재선 업데이트)
	public int updateApprovalReject(ApprovalLineVO approvalLineVO, HttpSession session) throws Exception {
		
		int result = 0;
		
		// approvalLineVO : documentId, rejectReason, userId(접속자) 가져온 상태
		approvalLineVO.setStatus("반려");
		approvalLineVO.setHandlingTime(Timestamp.valueOf(LocalDateTime.now()));
		
		// 결재자 정보 업데이트
		result = documentDAO.updateApprovalReject(approvalLineVO);
		
		// 결재자 정보 가져오기 (
		approvalLineVO = documentDAO.getApprovalDetail(approvalLineVO);
		
		
		//------------------------------------------------------------
		UserVO user = (UserVO)session.getAttribute("user"); // 접속자의 정보를 가져왔다 
		user = documentDAO.getUserDetail(user); // 접속자의 userId를 이용해 DB로부터 user에 데이터를 담아온다
		
		//
		DocumentVO documentVO = new DocumentVO(); 
		documentVO.setDocumentId(approvalLineVO.getDocumentId()); // documentId 전달할 그릇 생성
		
		documentVO = documentDAO.getDetail(documentVO);  // 그릇 재활용하여 업데이트 대상인 문서 정보 담아오기 (결재 전 문서 정보)
		
		
		// documentId 이용해서 그 문서의 결재선들 가져오기 (결재선 갯수 구하려고)
		List<ApprovalLineVO> childrenApprovers = documentDAO.getChildrenApprovers(documentVO);
		
		// 결재 관련 데이터를 변경해준다. 수정자 변수에 접속자 정보(수정하는 사람)도 넣어준다.		
		// 중간 결재자면 status 유지 및 문서의 step은 +1
		// 마지막 결재자면 status는 '결재완료'로 변경
		//System.out.println("childrenApprovers size : " + childrenApprovers.size());
		//System.out.println("appVO StepOrder : " + approvalLineVO.getStepOrder());
		
		documentVO.setCurrentStep(documentVO.getCurrentStep() + 1); 
		// +1을 하게 되면 반려자는 반려 리스트에서 볼 수 있고, 다음 결재자는 스텝이 같아지지만 status가 '반려'라 대기 목록에 보이지 않는다. 
		documentVO.setStatus("반려");
		// 문서가 "결재완료" 상태가 되면 작성자한테 결재완료 알림을 보낸다 
		notificationService.sendApprovalLine(documentVO, documentVO.getWriterId(), 2);
		
		documentVO.setModifierId(user.getUserId());
		documentVO.setModifierName(user.getName());
		documentVO.setModifierPosition(user.getPosition());
		documentVO.setModifierDept(user.getDeptName());
		documentVO.setModifierTime(approvalLineVO.getHandlingTime()); // 가장 최근 결재선의 처리 시간을 넣는다.
		
		
		// 문서 정보 업데이트
		result = documentDAO.updateDocumentProceed(documentVO); 			
		
		
		return result;
	}
	
	
	
	
	

}
