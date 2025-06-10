package com.coffice.app.posts.board;

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
		for(CommentVO c : boardVO.getComments()) {
			log.info("commentVO detail : {}", c);
		}
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
	
	@PostMapping("add")
	public String add(BoardVO boardVO, Model model) throws Exception{
		int result = boardService.add(boardVO);
		
		return "redirect:./list";
	}
	
	@PostMapping("delete")
	public String delete(BoardVO boardVO, Model model) throws Exception{
		int result = boardService.delete(boardVO);
		model.addAttribute("result", result);
		
		return "commons/ajaxResult";
	}
	
	@GetMapping("update")
	public String updateView(BoardVO boardVO, Model model) throws Exception{
		BoardVO boarVO = boardService.getDetail(boardVO);
		
		model.addAttribute("update", boarVO);
		
		return "board/update";
	}
	
	@PostMapping("update")
	public String update(BoardVO boardVO, Model model) throws Exception{
		int result = boardService.update(boardVO);
		if(result != 1) {
			model.addAttribute("result", "글 수정을 실패하였습니다.");
			model.addAttribute("path", "./list");
		}
		return "redirect:./detail?boardNum=" + boardVO.getBoardNum();
	}
	
	@PostMapping("addComment")
	@ResponseBody
	public CommentVO addComment(CommentVO commentVO) throws Exception{
		//log.info("{}", commentVO);
		return boardService.addComment(commentVO);
	}
	
	@PostMapping("reply")
	@ResponseBody
	public CommentVO reply(CommentVO commentVO, Model model) throws Exception{
		log.info("reply : {}", commentVO);
		//작성자 넣기
		commentVO = boardService.reply(commentVO);
		return commentVO;
	}
	
	@GetMapping("replyList")
	@ResponseBody
	public List<CommentVO> replyList(CommentVO commentVO) throws Exception{
		//log.info("commentVO num : {}", commentVO);
		return boardService.replyList(commentVO);
	}
	
	@PostMapping("commentDelete")
	public String commentDelete(CommentVO commentVO, Model model) throws Exception{
		log.info("del : {}", commentVO);
		int result = boardService.commentDelete(commentVO);
		model.addAttribute("result", result);
		return "commons/ajaxResult";
	}
}
