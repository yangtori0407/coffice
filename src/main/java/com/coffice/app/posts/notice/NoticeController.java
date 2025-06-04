package com.coffice.app.posts.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.page.Pager;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/notice/")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping("list")
	public String getList(Model model, Pager pager) throws Exception{
		List<NoticeVO> list = noticeService.getList(pager);
		log.info("List size : {}", list.size());
		model.addAttribute("list", list);
		model.addAttribute("pager", pager);
		
		return "notice/list";
	}
	
	@GetMapping("detail")
	public String getDetail(NoticeVO noticeVO, Model model) throws Exception{
		noticeVO = noticeService.getDetail(noticeVO);
		model.addAttribute("detail", noticeVO);
		
		return "notice/detail";
	}
	
	@GetMapping("add")
	public String add() throws Exception{
		return "notice/add";
	}
	
	@PostMapping("add")
	public String add(NoticeVO noticeVO) throws Exception{
		noticeService.add(noticeVO);
		return "notice/add";
	}
	
	@PostMapping("quillUpload")
	public String quillUpload(@RequestParam("uploadFile") MultipartFile multipartFile, Model model) throws Exception{
		String route = noticeService.quillUpload(multipartFile);
		model.addAttribute("result", route);
		
		return "commons/ajaxResult";
	}
}
