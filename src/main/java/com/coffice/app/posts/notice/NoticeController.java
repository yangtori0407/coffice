package com.coffice.app.posts.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.files.FileDownView;
import com.coffice.app.files.FileVO;
import com.coffice.app.notification.NotificationController;
import com.coffice.app.notification.NotificationService;
import com.coffice.app.page.Pager;
import com.coffice.app.users.UserVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/notice/")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private FileDownView fileDownView;
	
	
	@ModelAttribute("posts")
	public String kind() {
		return "notice";
	}
	
	@GetMapping("list")
	public String getList(Model model, Pager pager) throws Exception{
		List<NoticeVO> list = noticeService.getList(pager);
		//log.info("List size : {}", list.size());
		model.addAttribute("list", list);
		model.addAttribute("pager", pager);
		model.addAttribute("kind", "게시판 > 공지사항");
		return "notice/list";
	}
	
	@GetMapping("detail")
	@Transactional
	public String getDetail(NoticeVO noticeVO, Model model, Authentication authentication) throws Exception{
		
		noticeVO = noticeService.getDetail(noticeVO);
		
		if(noticeVO == null) {
			model.addAttribute("path", "/notice/list");
			model.addAttribute("result", "접근할 수 없는 공지사항 입니다.");
			return "commons/result";
		}
		
		noticeService.readNoticeNotification(noticeVO, authentication.getName());
		
		if(noticeVO.getDeleteStatus() == 1) {
			model.addAttribute("path", "/notice/list");
			model.addAttribute("result", "접근할 수 없는 공지사항 입니다.");
			return "commons/result";
		}
		
		model.addAttribute("detail", noticeVO);
		model.addAttribute("kind", "게시판 > 공지사항");
		return "notice/detail";
	}
	
	@GetMapping("add")
	public String add(Model model) throws Exception{
		model.addAttribute("kind", "게시판 > 공지사항 > 작성하기");
		return "notice/add";
	}
	
	@PostMapping("add")
	public String add(NoticeVO noticeVO, @RequestParam(value="attaches", required = false)MultipartFile[] attaches, Authentication authentication, Model model) throws Exception{
		UserVO userVO = (UserVO)authentication.getPrincipal();
		noticeVO.setUserId(userVO.getUserId());
		int result = noticeService.add(noticeVO, attaches);
		model.addAttribute("result", result);
		
		return "commons/ajaxResult";
		
	}
	
	@PostMapping("quillUpload")
	public String quillUpload(@RequestParam("uploadFile") MultipartFile multipartFile, Model model) throws Exception{
		String route = noticeService.quillUpload(multipartFile);
		model.addAttribute("result", route);
		
		return "commons/ajaxResult";
	}
	
	@GetMapping("fileDown")
	public FileDownView fileDown(NoticeFilesVO filesVO,Model model) throws Exception{
		FileVO fileVO = (FileVO)noticeService.fileDown(filesVO);
		log.info("파일 다운로드 : {}", fileVO.getOriginName());
		model.addAttribute("fileVO", fileVO);
		model.addAttribute("kind", "notice");
		
		return fileDownView;
	}
	
	@PostMapping("delete")
	public String delete(NoticeVO noticeVO, Model model) throws Exception{
		int result = noticeService.delete(noticeVO);
		
		model.addAttribute("result", result);
		
		return "commons/ajaxResult";
	}
	
	@GetMapping("update")
	public String update(NoticeVO noticeVO, Model model, Authentication authentication) throws Exception{
		noticeVO = noticeService.getDetail(noticeVO);
		if(noticeVO == null || !noticeVO.getUserId().equals(authentication.getName())) {
			model.addAttribute("path", "/notice/list");
			model.addAttribute("result", "잘못된 접근입니다.");
			return "commons/result";
		}
		model.addAttribute("update", noticeVO);
		model.addAttribute("kind", "게시판 > 공지사항 > 수정하기");
		return "notice/update";
	}
	
	@PostMapping("update")
	public String update(NoticeVO noticeVO, @RequestParam(value = "attaches", required = false) MultipartFile[] attaches,int[] deleteFile, Model model) throws Exception{
		
//		log.info("noticeVO : {}", noticeVO);
//		log.info("attaches : {}", attaches);
//		log.info("deleteFile : {}", deleteFile);
		
		int result = noticeService.update(noticeVO, attaches, deleteFile);
		model.addAttribute("result", result);
		return "commons/ajaxResult";
	}
}
