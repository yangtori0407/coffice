package com.coffice.app.posts.notice;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coffice.app.page.Pager;

@Mapper
public interface NoticeDAO {
	
	public int add(NoticeVO noticeVO) throws Exception;
	public List<NoticeVO> getList(Pager pager) throws Exception;
	public Long getTotalCount(Pager pager) throws Exception;
	public NoticeVO getDetail(NoticeVO noticeVO) throws Exception;
	public int quillUpload(NoticeFilesVO noticeFilesVO) throws Exception;
	public int addFiles(NoticeFilesVO filesVO) throws Exception;
	public NoticeFilesVO fileDetail(NoticeFilesVO filesVO) throws Exception;
	public int delete(NoticeVO noticeVO) throws Exception;
	public int update(NoticeVO noticeVO) throws Exception;
	public int updateFile(int[] deleteFile) throws Exception;
	public int deleteFile(NoticeVO noticeVO) throws Exception;
}
