package com.coffice.app.documents;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.documents.attachments.AttachmentVO;
import com.coffice.app.documents.forms.FormVO;
import com.coffice.app.pagers.Pager;
import com.coffice.app.users.UserVO;

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
		
		if(vo == null) {
			
		}
		
		model.addAttribute(vo);
		
		return "document/detail";
	}
	
	
	//
	@GetMapping("add")
	public String add(Model model) throws Exception {
		
		// 유저 리스트 조회
		List<UserVO> users = documentService.getUsers();
		
		// 폼 리스트 조회
		List<FormVO> forms = documentService.getForms();
		
		for(FormVO form : forms) {
			System.out.println("id : " + form.getFormId());
		}
		
		model.addAttribute("users", users);
		model.addAttribute("forms", forms);
		
		return "document/addSetting";
	}
	
	
	//
	@PostMapping("add")
	public String add(DocumentVO documentVO, MultipartFile[] attaches, UserVO userVO) throws Exception {
		
		documentVO.setUserId(userVO.getUserId());
		
		int result = documentService.add(documentVO, attaches);
		
		return "redirect:./list -> 기안문서함";
	}
	
	
	//
	public String getFileDetail(AttachmentVO attachmentVO, Model model) throws Exception {
		
		attachmentVO = documentService.getFileDetail(attachmentVO);
		
		model.addAttribute("attachmentVO", attachmentVO);
		
		return "fileDownView";
	}
	
	

}
