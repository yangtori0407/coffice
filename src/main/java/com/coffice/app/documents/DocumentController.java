package com.coffice.app.documents;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.documents.attachments.AttachmentVO;
import com.coffice.app.documents.forms.FormVO;
import com.coffice.app.documents.lines.ApprovalLineVO;
import com.coffice.app.documents.lines.ReferenceLineVO;
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
	private DocumentService documentService;

	//
	@GetMapping("tempLogin1")
	public String tempLogin1(HttpSession session) {
		// 테스트용 임시 유저
		UserVO userVO = new UserVO();
		userVO.setUserId("아디1");
		userVO.setName("브라움");
		userVO.setDeptName("인사팀");
		userVO.setPosition("사원");
		session.setAttribute("userVO", userVO);

		return "redirect:./selectform";
	}

	//
	@GetMapping("tempLogin2")
	public String tempLogin2(HttpSession session) {
		// 테스트용 임시 유저
		UserVO userVO = new UserVO();
		userVO.setUserId("아디3");
		userVO.setName("소라카");
		userVO.setDeptName("인사팀");
		userVO.setPosition("대리");
		session.setAttribute("userVO", userVO);

		return "redirect:./selectform";
	}

	//
	@GetMapping("tempLogin3")
	public String tempLogin3(HttpSession session) {
		// 테스트용 임시 유저
		UserVO userVO = new UserVO();
		userVO.setUserId("아디4");
		userVO.setName("소나");
		userVO.setDeptName("인사팀");
		userVO.setPosition("과장");
		session.setAttribute("userVO", userVO);

		return "redirect:./selectform";
	}

	//
	@GetMapping("tempLogin4")
	public String tempLogin4(HttpSession session) {
		// 테스트용 임시 유저
		UserVO userVO = new UserVO();
		userVO.setUserId("아디6");
		userVO.setName("쓰레쉬");
		userVO.setDeptName("인사팀");
		userVO.setPosition("부장");
		session.setAttribute("userVO", userVO);

		return "redirect:./selectform";
	}

	//
	@GetMapping("tempLogout")
	public String tempLogout(HttpSession session) {

		session.invalidate();

		return "redirect:./selectform";
	}

	//
	@PostMapping("form")
	@ResponseBody
	public FormVO formDetail(FormVO formVO) throws Exception {

		FormVO resultVO = documentService.formDetail(formVO);

		return resultVO;
	}

	//
	@GetMapping("list/*")
	public String getList(Pager pager, Model model, HttpServletRequest request, HttpSession session) throws Exception {

		List<DocumentVO> list = documentService.getList(pager, request, session);
		model.addAttribute("list", list);
		System.out.println("list size : " + list.size());
		model.addAttribute("pager", pager);

		return "document/list";
	}

	//
	@GetMapping("detail") // 임시저장 문서로 넘어갈 때는 수정 가능하도록 조건을 나누어야한다.
	public String getDetail(DocumentVO documentVO, Model model, HttpSession session) throws Exception {

		//
		DocumentVO vo = documentService.getDetail(documentVO);

		if (vo == null) {
			System.out.println("document detail vo가 null입니다");
		}
		model.addAttribute("vo", vo);

		// 문서 정보 가져올 때, 접속자의 직인 정보들도 모달에 뿌려놓는다 (모달창 열면 서버 다시 안가고 바로 뜨도록)
		List<SignVO> signList = documentService.getSignList(session);
		model.addAttribute("signList", signList);

		return "document/form/paymentDetail";
	}

	
	// 양식 선택 화면 요청 (GET)
	@GetMapping("selectform")
	public String selectform(Model model) throws Exception {
		
		List<FormVO> formList = documentService.getFormsIdName();
		model.addAttribute("formList", formList);
		
		return "document/form/selectForm";
	}
	
	// 선택한 양식 출력 요청 (GET)
	@GetMapping("write")
	public String write(Model model, HttpSession session, FormVO formVO) throws Exception {
				
		// 처음 양식 선택 시 보낸 formId와 formName을 받아온다 
		model.addAttribute("formVO",formVO);
		
		int isWritePage = 1;
		model.addAttribute("isWritePage", isWritePage);
		
		LocalDateTime time = LocalDateTime.now();		 
		model.addAttribute("timeNow", time);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
		String formattedDate = time.format(formatter);
		model.addAttribute("formattedDate", formattedDate);

		
		return "document/form/inAndOutConfirm";
	}
	
	
	//
	@PostMapping("write")
	public String add(DocumentVO documentVO, @RequestParam("approvers") String approversJson, @RequestParam("referrers") String referrersJson, 
			HttpSession session, MultipartFile[] files) throws Exception {
		

		ObjectMapper mapper = new ObjectMapper();
	    List<ApprovalLineVO> approverList = mapper.readValue(approversJson, new TypeReference<List<ApprovalLineVO>>() {});
	    List<ReferenceLineVO> referrerList = mapper.readValue(approversJson, new TypeReference<List<ReferenceLineVO>>() {});
	    

		// 서비스 메서드 실행
		int result = documentService.add(documentVO, approverList, referrerList, files, session);

		return "redirect:./list/online";
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
	public String getFileDetail(AttachmentVO attachmentVO, Model model) throws Exception {

		attachmentVO = documentService.getFileDetail(attachmentVO);

		model.addAttribute("attachmentVO", attachmentVO);

		return "fileDownView";
	}

	//
	@PostMapping("proceed")
	public String updateApprovalProceed(ApprovalLineVO approvalLineVO, HttpSession session) throws Exception {

		int result = documentService.updateApprovalProceed(approvalLineVO, session);

		return "redirect:./detail?documentId=" + approvalLineVO.getDocumentId();
	}

}
