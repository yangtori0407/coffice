package com.coffice.app.posts.board;

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
@RequestMapping("/board/")
@Slf4j
public class BoardController {
	
	@Autowired
	private BoardSerivce boardService;
	
	@GetMapping("list")
	public String getList(Model model, Pager pager) throws Exception{
		List<BoardVO> list = boardService.getList(pager);
		
		model.addAttribute("list", list);
		
		return "board/list";
	}
	
	@GetMapping("detail")
	public String getDetail(Model model, BoardVO boardVO) throws Exception{
		boardVO = boardService.getDetail(boardVO);
		model.addAttribute("detail", boardVO);
		
		return "board/detail";
	}
	
	@PostMapping("quillUpload")
	public String quillUpload(@RequestParam("uploadFile")MultipartFile file, Model model) throws Exception{
		String result = boardService.quillUpload(file);
		model.addAttribute("result", result);
		
		return "commons/ajaxResult";
	}
	
	@GetMapping("add")
	public String add() throws Exception{
		return "board/add";
	}
}
