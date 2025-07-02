package com.coffice.app.posts.board;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.files.FileManager;
import com.coffice.app.notification.NotificationService;
import com.coffice.app.page.Pager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardSerivce {
	
	@Autowired
	private BoardDAO boardDAO;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private FileManager fileManager;
	
	@Value("${app.files.base}")
	private String path;
	
	public List<BoardVO> getList(Pager pager) throws Exception{
		pager.make();
		pager.makeNum(boardDAO.getTotalCount(pager));
		List<BoardVO> list = boardDAO.getList(pager);
		
		return list;
	}
	
	@Transactional
	public BoardVO getDetail(BoardVO boardVO) throws Exception{
		boardVO = boardDAO.getBoardDetail(boardVO);
		log.info("boardVO : {}", boardVO);
		if(boardVO != null) {
			
			List<CommentVO> list = boardDAO.getCommentList(boardVO);
			boardVO.setComments(list);
			return boardVO;
		}
		return null;
	}

	public String quillUpload(MultipartFile file) throws Exception{
		String fileName = "";
		if(!file.isEmpty()) {
			fileName = fileManager.fileSave(path.concat("quill"), file);
			
//			QuillFileVO quillFileVO = new QuillFileVO();
//			quillFileVO.setSaveName(fileName);
//			quillFileVO.setOriginName(file.getOriginalFilename());
//			
//			boardDAO.quillUpload(quillFileVO);
			//나중에 넣었다가 지운 애들을 삭제 할지 말지 결정하기
		}
		
		return "quill\\" + fileName;
	}

	public int add(BoardVO boardVO) throws Exception{
		int result = boardDAO.add(boardVO);
		return 0;
	}

	public int delete(BoardVO boardVO) throws Exception{
		return boardDAO.delete(boardVO);
	}

	public int update(BoardVO boardVO) throws Exception{
		// TODO Auto-generated method stub
		return boardDAO.update(boardVO);
	}

	public CommentVO addComment(CommentVO commentVO) throws Exception{
		boardDAO.addComment(commentVO);
		commentVO = boardDAO.detailComment(commentVO);
		BoardVO boardVO = boardDAO.getBoardInfoByBoardNum(commentVO.getBoardNum());
		notificationService.sendComment(commentVO, boardVO);
		
		return commentVO;
	}

	public CommentVO reply(CommentVO commentVO) throws Exception{
		int result = boardDAO.addReply(commentVO);
		
		commentVO = boardDAO.replyDetail(commentVO);
		CommentVO p = boardDAO.getParentComment(commentVO.getCommentP());
		notificationService.sendReply(commentVO, p);
		
		return commentVO;
	}

	public List<CommentVO> replyList(CommentVO commentVO) throws Exception{
		// TODO Auto-generated method stub
		return boardDAO.replyList(commentVO);
	}

	public int commentDelete(CommentVO commentVO) throws Exception{
		return boardDAO.commentDelete(commentVO);
		
	}

	public CommentVO commentUpdate(CommentVO commentVO) throws Exception{
		boardDAO.commentUpdate(commentVO);
		return boardDAO.detailComment(commentVO);
		
	}
	
	public void readBoardNotification(BoardVO boardVO, String userId) throws Exception{
		notificationService.checkBoardNotification(boardVO, userId);
	}
}
