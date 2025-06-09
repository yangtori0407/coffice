package com.coffice.app.documents;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.coffice.app.users.UserVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/document/*")
public class DocumentController {

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
	@GetMapping("list")
	public String getList(Pager pager, Model model) throws Exception {

		List<DocumentVO> list = documentService.getList(pager);
		model.addAttribute(list);
		model.addAttribute(pager);

		return "document/list";
	}

	//
	@GetMapping("detail") // 임시저장 문서로 넘어갈 때는 수정 가능하도록 조건을 나누어야한다.
	public String getDetail(DocumentVO documentVO, Model model) throws Exception {

		DocumentVO vo = documentService.getDetail(documentVO);

		if (vo == null) {

		}

		model.addAttribute(vo);

		return "document/detail";
	}

	// 양식 설정 요청 (GET)
	@GetMapping("make")
	public String make(Model model) throws Exception {

		// 유저 리스트 조회
		List<UserVO> users = documentService.getUsers();

		// 폼 리스트 조회
		List<FormVO> forms = documentService.getForms();

		for (FormVO form : forms) {
			System.out.println("id : " + form.getFormId());
		}

		model.addAttribute("users", users);
		model.addAttribute("forms", forms);

		return "document/makeSetting";
	}

	// 양식 설정 요청 (POST)
	@PostMapping("make")
	public String make(HttpSession session, Model model, FormVO formVO, @RequestParam String approvers,
			@RequestParam String referrers) throws Exception {

		// 파라미터 값들을 받아와서
		ObjectMapper mapper = new ObjectMapper();
		List<UserVO> approverList = mapper.readValue(approvers, new TypeReference<List<UserVO>>() {
		});
		List<UserVO> referrerList = mapper.readValue(referrers, new TypeReference<List<UserVO>>() {
		});

		/*
		 * System.out.println("formId : " + formVO.getFormId());
		 * 
		 * System.out.println("approversLength : " + approverList.size());
		 * if(approverList.size() != 0) {
		 * System.out.println(approverList.get(0).getUserId()); }
		 * 
		 * System.out.println("referrersLength : " + referrerList.size());
		 * if(referrerList.size() != 0) {
		 * System.out.println(referrerList.get(0).getUserId()); }
		 */

		model.addAttribute("formVO", formVO);
		model.addAttribute("approvers", approverList);
		model.addAttribute("referrers", referrerList);

		// 테스트용 임시 유저
		UserVO userVO = new UserVO();
		userVO.setUserId("사원아디");
		userVO.setName("쓴이 이름1");
		userVO.setDeptId(1);
		userVO.setPosition("쓴이 직급1");
		session.setAttribute("userVO", userVO);

		session.setAttribute("sessionApprovers", approverList);
		session.setAttribute("sessionReferrers", referrerList);

		// formVO 안의 formId에 따라 return 다르게 주기
		if (formVO.getFormId() == 10) {

			return "document/form10";

		} else if (formVO.getFormId() == 11) {

			return "document/form11";

		} else if (formVO.getFormId() == 12) {

			return "document/form12";

		} else if (formVO.getFormId() == 13) {

			return "document/form13";

		} else {
			System.out.println("없는 양식입니다");
			return "index";
		}

	}

	//
	@PostMapping("write")
	public String add(HttpSession session, DocumentVO documentVO, MultipartFile[] attaches) throws Exception {

		List<UserVO> approverList = (List<UserVO>) session.getAttribute("sessionApprovers");
		List<UserVO> referrerList = (List<UserVO>) session.getAttribute("sessionReferrers");

		// documentVO에 데이터들 넣기

		documentVO.setTime(Timestamp.valueOf(LocalDateTime.now()));
		documentVO.setCurrentStep(1L);
		documentVO.setStatus("결재 중");

		
		//
		List<ApprovalLineVO> aList = new ArrayList<>();
		Long step = 0L;

		for (UserVO vo : approverList) {
			ApprovalLineVO aLineVO = new ApprovalLineVO();
			aLineVO.setUserId(vo.getUserId());
			aLineVO.setStepOrder(step++);	System.out.println("step : " + step);		
			aLineVO.setStatus("결재대기");
			aList.add(aLineVO);
		}
		

		//
		List<ReferenceLineVO> rList = new ArrayList<>();

		for (UserVO vo : referrerList) {
			ReferenceLineVO rLineVO = new ReferenceLineVO();
			rLineVO.setUserId(vo.getUserId());
			rList.add(rLineVO);
		}
		
		
		// 서비스 메서드 실행
		int result = documentService.add(documentVO, aList, rList, attaches);

		return "redirect:./list -> 기안문서함";
	}

	
	//
	public String getFileDetail(AttachmentVO attachmentVO, Model model) throws Exception {

		attachmentVO = documentService.getFileDetail(attachmentVO);

		model.addAttribute("attachmentVO", attachmentVO);

		return "fileDownView";
	}

}
