package com.coffice.app.posts.notice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NoticeDAOTest {
	
	@Autowired
	private NoticeDAO noticeDAO;
	@Test
	void testAdd() throws Exception{
		for(int i = 1; i <= 100;i++) {
			NoticeVO noticeVO = new NoticeVO();
			noticeVO.setUserId("TEST");
			noticeVO.setNoticeTitle("NoticeTitle" + i);
			noticeVO.setNoticeContents("NoticeContents"+ i);
			
			noticeDAO.add(noticeVO);
		}
	}

}
