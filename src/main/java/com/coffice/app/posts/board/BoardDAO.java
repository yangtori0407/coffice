package com.coffice.app.posts.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coffice.app.page.Pager;

@Mapper
public interface BoardDAO {

	public List<BoardVO> getList(Pager pager) throws Exception;
	
	public int add(BoardVO boardVO) throws Exception;
	
	public Long getTotalCount(Pager pager) throws Exception;

	public BoardVO getDetail(BoardVO boardVO) throws Exception;

	public int delete(BoardVO boardVO) throws Exception;

	public int update(BoardVO boardVO) throws Exception;

	public int addComment(CommentVO commentVO) throws Exception;

	public CommentVO detailComment(CommentVO commentVO) throws Exception;

	public int refInit(CommentVO commentVO) throws Exception;

	public int updateStep(CommentVO parent) throws Exception;
	
	public int addReply(CommentVO commentVO) throws Exception;

	public List<CommentVO> replyList(CommentVO commentVO) throws Exception;

	public CommentVO replyDetail(CommentVO commentVO) throws Exception;

	public int commentDelete(CommentVO commentVO) throws Exception;

	public int commentUpdate(CommentVO commentVO) throws Exception;

	public BoardVO getBoardInfoByBoardNum(Long boardNum) throws Exception;

	public CommentVO getParentComment(Long commentP) throws Exception;

	public BoardVO getBoardDetail(BoardVO boardVO) throws Exception;

	public List<CommentVO> getCommentList(BoardVO boardVO) throws Exception;


}
