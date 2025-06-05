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

}
