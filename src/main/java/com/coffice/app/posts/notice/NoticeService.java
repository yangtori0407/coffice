package com.coffice.app.posts.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffice.app.page.Pager;

@Service
public class NoticeService {
	
	@Autowired
	private NoticeDAO noticeDAO;
	
	public List<NoticeVO> getList(Pager pager) throws Exception{
		pager.make();
		pager.makeNum(noticeDAO.getTotalCount(pager));
		return noticeDAO.getList(pager);
	}
	
	public NoticeVO getDetail(NoticeVO noticeVO) throws Exception{
		return noticeDAO.getDetail(noticeVO);
	}
}
