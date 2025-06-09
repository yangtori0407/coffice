package com.coffice.app.posts.board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coffice.app.posts.notice.NoticeVO;

@SpringBootTest
class BoardDAOTest {
	
	@Autowired
	private BoardDAO boardDAO;

	@Test
	void testGetList() throws Exception{
		for(int i = 1; i <= 100;i++) {
			BoardVO boardVO = new BoardVO();
			boardVO.setUserId("TEST");
			boardVO.setBoardTitle("BoardTitle " + i);
			boardVO.setBoardContents("BoardContents " + i);
			
			boardDAO.add(boardVO);
		}
	}

}
