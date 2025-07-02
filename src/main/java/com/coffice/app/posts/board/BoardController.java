package com.coffice.app.posts.board;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.notification.NotificationService;
import com.coffice.app.page.Pager;
import com.coffice.app.users.UserVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board/")
@Slf4j
public class BoardController {
	
	@Autowired
	private BoardSerivce boardService;
	
	@ModelAttribute("posts")
	public String posts() {
		return "board";
	}
	
	@GetMapping("list")
	public String getList(Model model, Pager pager) throws Exception{
		List<BoardVO> list = boardService.getList(pager);
		
		model.addAttribute("list", list);
		model.addAttribute("kind", "게시판 > 익명게시판");
		
		return "board/list";
	}
	
	@GetMapping("detail")
	@Transactional
	public String getDetail(Model model, BoardVO boardVO, Authentication authentication) throws Exception{
		boardVO = boardService.getDetail(boardVO);
		
		if(boardVO == null) {
			model.addAttribute("path", "/board/list");
			model.addAttribute("result", "접근할 수 없는 글입니다.");
			return "commons/result";
		}
		boardService.readBoardNotification(boardVO, authentication.getName());
		
		if(boardVO.getDeleteStatus() == 1) {
			model.addAttribute("path", "/board/list");
			model.addAttribute("result", "접근할 수 없는 글입니다.");
			return "commons/result";
		}
		model.addAttribute("detail", boardVO);
//		for(CommentVO c : boardVO.getComments()) {
//			log.info("commentVO detail : {}", c);
//		}
		model.addAttribute("kind", "게시판 > 익명게시판");
		return "board/detail";
	}
	
	@PostMapping("quillUpload")
	public String quillUpload(@RequestParam("uploadFile")MultipartFile file, Model model) throws Exception{
		String result = boardService.quillUpload(file);
		model.addAttribute("result", result);
		
		return "commons/ajaxResult";
	}
	
	@GetMapping("add")
	public String add(Model model) throws Exception{
		model.addAttribute("kind", "게시판 > 익명게시판 > 작성하기");
		return "board/add";
	}
	
	@PostMapping("add")
	public String add(BoardVO boardVO, Model model, Authentication authentication) throws Exception{
		UserVO userVO = (UserVO)authentication.getPrincipal();
		boardVO.setUserId(userVO.getUserId());
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
	public String updateView(BoardVO boardVO, Model model, Authentication authentication) throws Exception{
		boardVO = boardService.getDetail(boardVO);
		log.info("{}", boardVO);
		if(boardVO == null || !boardVO.getUserId().equals(authentication.getName())) {
			model.addAttribute("path", "/board/list");
			model.addAttribute("result", "접근 권한이 없습니다.");
			return "commons/result";
		}
		model.addAttribute("update", boardVO);
		model.addAttribute("kind", "게시판 > 익명게시판 > 수정하기");
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
	public CommentVO addComment(CommentVO commentVO, Authentication authentication) throws Exception{
		UserVO userVO = (UserVO)authentication.getPrincipal();
		//log.info("addCommentVO : {}", commentVO);
		
		commentVO.setUserId(userVO.getUserId());
		commentVO = boardService.addComment(commentVO);
		
		return commentVO;
	}
	
	@PostMapping("reply")
	@ResponseBody
	public CommentVO reply(CommentVO commentVO, Model model, Authentication authentication) throws Exception{
		//log.info("reply : {}", commentVO);
		//작성자 넣기
		UserVO userVO = (UserVO)authentication.getPrincipal();
		commentVO.setUserId(userVO.getUserId());
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
		//log.info("del : {}", commentVO);
		int result = boardService.commentDelete(commentVO);
		model.addAttribute("result", result);
		return "commons/ajaxResult";
	}
	
	@PostMapping("commentUpdate")
	@ResponseBody
	public CommentVO commentUpdate(CommentVO commentVO) throws Exception{
		commentVO = boardService.commentUpdate(commentVO);
		
		return commentVO;
	}
}
