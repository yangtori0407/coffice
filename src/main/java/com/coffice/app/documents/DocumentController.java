package com.coffice.app.documents;

import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.documents.attachments.AttachmentVO;
import com.coffice.app.documents.forms.FormVO;
import com.coffice.app.documents.lines.ApprovalLineVO;
import com.coffice.app.documents.lines.ReferenceLineVO;
import com.coffice.app.files.FileDownView;
import com.coffice.app.notification.NotificationService;
import com.coffice.app.page.Pager;
import com.coffice.app.signs.SignVO;
import com.coffice.app.users.UserVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/document/*")
public class DocumentController {

	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private FileDownView fileDownView;
	
	@Autowired
	private DocumentService documentService;

	//
	@PostMapping("form")
	@ResponseBody
	public FormVO formDetail(FormVO formVO) throws Exception {

		FormVO resultVO = documentService.formDetail(formVO);

		return resultVO;
	}

	//
	@PreAuthorize("principal.status != null and principal.status == 1")
	@GetMapping("list/*")
	public String getList(Pager pager, Model model, HttpServletRequest request, HttpSession session) throws Exception {

		String uri = request.getRequestURI();		
		String[] parts = uri.split("/");
		String url = parts[parts.length-1];
		
		model.addAttribute("url", url);
		
		model.addAttribute("pager", pager);
		
		// 리스트 가져오기
		Map<String, Object> map = documentService.getList(pager, request, session);
		
		List<DocumentVO> docuList = (List<DocumentVO>)map.get("list");
		System.out.println("list 컨트롤러 docuList size : " + docuList.size());
		
		String kind = (String)map.get("kind");
		String docuKind = (String)map.get("docuKind");
		
		model.addAttribute("docuList", docuList);
		model.addAttribute("kind", kind);
		model.addAttribute("docuKind", docuKind);
		
		
		return "document/list";
	}

	
	//
	@PreAuthorize("principal.status != null and principal.status == 1")
	@GetMapping("detail") 
	public String getDetail(DocumentVO documentVO, Model model, HttpSession session) throws Exception {

		//
		DocumentVO docuVO = documentService.getDetail(documentVO);
		
		if (docuVO == null) {
			System.out.println("document detail docuVO가 null입니다");
		}
		
		
		
		model.addAttribute("docuVO", docuVO);

		// 문서 정보 가져올 때, 접속자의 직인 정보들도 모달에 뿌려놓는다 (모달창 열면 서버 다시 안가고 바로 뜨도록)
		List<SignVO> signList = documentService.getSignList(session);
		model.addAttribute("signList", signList);

		// 임시저장 페이지에 보여줄 표시용 시간도 보낸다
		LocalDate fakeToday = LocalDate.now();
		model.addAttribute("fakeToday", fakeToday);
		
		
		UserVO sessionUser = (UserVO)session.getAttribute("user");
		String kindMessage = "";
		String docuKind = "";
		
		
		if(docuVO.getStatus().equals("임시저장")) {
			kindMessage = "결재 > 임시 저장 문서";
			docuKind = "ontemporary";
			
		} else if(docuVO.getWriterId().equals(sessionUser.getUserId())) {
			kindMessage = "결재 > 기안 문서";
			docuKind = "online";
			
		} else {
			
				for(ReferenceLineVO line : docuVO.getReferenceLineVOs()) {
					if(line.getUserId().equals(sessionUser.getUserId()) && !(docuVO.getStatus().equals("임시저장"))) {
						
						kindMessage = "결재 > 참조 문서";
						docuKind = "onreference";
					}
				}
				
				for(ApprovalLineVO line : docuVO.getApprovalLineVOs()) {
					if(line.getStepOrder() < docuVO.getCurrentStep() && line.getUserId().equals(sessionUser.getUserId())) {
						
						kindMessage = "결재 > 승인/반려 문서";
						docuKind = "handled";
					}
				}
				
				for(ApprovalLineVO line : docuVO.getApprovalLineVOs()) {
					if(line.getStepOrder() == docuVO.getCurrentStep() && line.getUserId().equals(sessionUser.getUserId())) {
						
						kindMessage = "결재 > 결재 대기 문서";
						docuKind = "onwaiting";
					}
				}
				
		}
		
		
		model.addAttribute("kind", kindMessage);
		model.addAttribute("docuKind", docuKind);
		
		return "document/form/variableForm";
	}

	
	// 양식 선택 화면 요청 (POST)
	@PostMapping("selectform")
	@ResponseBody
	public List<FormVO> selectform(Model model) throws Exception {
		
		List<FormVO> formList = documentService.getFormsIdName();		
		
		return formList;
	}
	
	
	// 선택한 양식 출력 요청 (GET)
	@PreAuthorize("principal.status != null and principal.status == 1")
	@GetMapping("write")
	public String write(Model model, HttpSession session, FormVO formVO) throws Exception {
				
		// 처음 양식 선택 시 보낸 formId를 이용해서 form 정보를 조회 해온다.
		formVO = documentService.formDetail(formVO);		
		model.addAttribute("formVO",formVO);
		
		LocalDate fakeToday = LocalDate.now();
		model.addAttribute("fakeToday", fakeToday);
		
		model.addAttribute("isWritePage", 1);
		model.addAttribute("kind", "결재 > 문서 작성");
		model.addAttribute("docuKind", "write");
		return "document/form/variableForm";
	}
	
	
	//
	@PreAuthorize("principal.status != null and principal.status == 1")
	@PostMapping("write")
	@ResponseBody
	public String add(DocumentVO documentVO, @RequestParam("approvers") String approversJson, @RequestParam("referrers") String referrersJson, 
			MultipartFile[] attaches) throws Exception {
		
		// Json 형식으로 받아온 결재선, 참조선 데이터를 각 타입에 맞게 넣어준다
		ObjectMapper mapper = new ObjectMapper();
	    List<ApprovalLineVO> approverList = mapper.readValue(approversJson, new TypeReference<List<ApprovalLineVO>>() {});
	    List<ReferenceLineVO> referrerList = mapper.readValue(referrersJson, new TypeReference<List<ReferenceLineVO>>() {});
	    
	    if(attaches != null) {
	    	System.out.println("attaches size : " + attaches.length);
	    	
	    }

		// 서비스 메서드 실행
		int result = documentService.add(documentVO, approverList, referrerList, attaches);
		
		// "완료" 또는 "임시저장"에 따라 리스트 페이지 리턴 경로를 다르게 준다		
		if(documentVO.getStatus().equals("임시저장")) {
			return "./list/ontemporary";
			
		} else {
			// 데이터 다 처리하고, 페이지 넘기기 전에 알림(Notification) 메서드 실행해준다.
	    	// 1. 참조자 알림 보내기
	    	notificationService.sendReferrenceLine(documentVO, referrerList);
	    	
	    	// 2. 기안이 올라가면서 첫번째 결재자한테 알림 보내기
	    	notificationService.sendApprovalLine(documentVO, approverList.get(0).getUserId(), 1);
			
			return "./list/online";
			
		}
	}
	
	
	//
	@PostMapping("deletetemp")
	public String deleteTemp(DocumentVO documentVO) throws Exception {
		
		int result = documentService.deleteTemp(documentVO);
		
		return "redirect:./list/ontemporary";
	}
	
	
	//
	@PostMapping("updateonlystatus")
	public String updateOnlyStatus(DocumentVO documentVO) throws Exception {
		
		int result = documentService.updateOnlyStatus(documentVO);
		
		
		return "redirect:./list/ontemporary";
	}
	
	
	//
	@PostMapping("updatetemp")
	@ResponseBody
	public String updateTemp(DocumentVO documentVO, @RequestParam("approvers") String approversJson, @RequestParam("referrers") String referrersJson, 
			MultipartFile[] attaches, Long[] exists) throws Exception {
		
		if(attaches != null) {
	    	System.out.println("attaches size : " + attaches.length);	    	
	    } else {
	    	System.out.println("attaches null입니다 ");
	    }
		
		if(exists != null) {
	    	System.out.println("exists size : " + exists.length);	    	
	    } else {
	    	System.out.println("exists null입니다 ");
	    }
		
		//Json 형식으로 받아온 결재선, 참조선 데이터를 각 타입에 맞게 넣어준다
		ObjectMapper mapper = new ObjectMapper();
	    List<ApprovalLineVO> approverList = mapper.readValue(approversJson, new TypeReference<List<ApprovalLineVO>>() {});
	    List<ReferenceLineVO> referrerList = mapper.readValue(referrersJson, new TypeReference<List<ReferenceLineVO>>() {});
	    
	    // 서비스 메서드 실행
	    int result = documentService.updateTemp(documentVO, approverList, referrerList, attaches, exists);
		
	    if(documentVO.getStatus().equals("임시저장")) {
	    	return "./list/ontemporary";
	    } else {
	    	// 데이터 다 처리하고, 페이지 넘기기 전에 알림(Notification) 메서드 실행해준다.
	    	// 1. 참조자 알림 보내기
	    	notificationService.sendReferrenceLine(documentVO, referrerList);
	    	
	    	// 2. 기안이 올라가면서 첫번째 결재자한테 알림 보내기
	    	notificationService.sendApprovalLine(documentVO, approverList.get(0).getUserId(), 1);
	    	
	    	return "./list/online";
	    }
	}
	

	//
	@PostMapping(value = "addSign", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	// 새 sign을 insert하고 전체 sign 목록을 다시 select해서 가져온다.
	public List<SignVO> addSign(HttpSession session, MultipartFile[] attaches) throws Exception {

		// 사인 추가
		int result = documentService.addSign(session, attaches);

		// 사인 리스트 조회
		List<SignVO> resultList = documentService.getSignList(session);

		return resultList;
	}

	//
	@PostMapping("filedown")
	public FileDownView getFileDetail(AttachmentVO attachmentVO, Model model) throws Exception {
		System.out.println("filedown 컨트롤러 fileNum : " + attachmentVO.getFileNum());
		attachmentVO = documentService.getFileDetail(attachmentVO);

		model.addAttribute("fileVO", attachmentVO);
		model.addAttribute("kind", "docuFiles");

		return fileDownView;
	}

	//
	@PostMapping("proceed")
	public String updateApprovalProceed(ApprovalLineVO approvalLineVO, HttpSession session) throws Exception {

		System.out.println("user "+approvalLineVO.getUserId());
		System.out.println("docu "+approvalLineVO.getDocumentId());
		System.out.println("sign "+approvalLineVO.getSignId());
		
		int result = documentService.updateApprovalProceed(approvalLineVO, session);

		return "redirect:./list/handled";
	}
	
	
	//
	@PostMapping("reject")
	public String updateApprovalReject(ApprovalLineVO approvalLineVO, HttpSession session) throws Exception {

		System.out.println("user "+approvalLineVO.getUserId());
		System.out.println("docu "+approvalLineVO.getDocumentId());
		System.out.println("reason "+approvalLineVO.getRejectReason());
		
		int result = documentService.updateApprovalReject(approvalLineVO, session);

		return "redirect:./list/handled";
	}
	
	
	
	
	

}
