package com.coffice.app.posts.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.files.FileManager;
import com.coffice.app.page.Pager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoticeService {
	
	@Autowired
	private FileManager fileManager;
	
	@Value("${app.files.base}")
	private String path;
	
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

	public void add(NoticeVO noticeVO, MultipartFile[] attaches) throws Exception{
		int result = noticeDAO.add(noticeVO);
		
		for(MultipartFile f : attaches) {
			if(f.isEmpty()) {
				continue;
			}
			
			String fileName = fileManager.fileSave(path.concat("notice"), f);
			NoticeFilesVO filesVO = new NoticeFilesVO();
			filesVO.setNoticeNum(noticeVO.getNoticeNum());
			filesVO.setSaveName(fileName);
			filesVO.setOriginName(f.getOriginalFilename());
			
			noticeDAO.addFiles(filesVO);
			
		}
	}
	
	public String quillUpload(MultipartFile multipartFile) throws Exception{
		String fileName = "";
		
		if(!multipartFile.isEmpty()) {
			fileName = fileManager.fileSave(path.concat("notice"), multipartFile);
			
			NoticeFilesVO filesVO = new NoticeFilesVO();
			filesVO.setSaveName(fileName);
			filesVO.setOriginName(multipartFile.getOriginalFilename());
			
			noticeDAO.quillUpload(filesVO);
		
		}
				
		return  "notice\\" + fileName;
	}
	
	public NoticeFilesVO fileDown(NoticeFilesVO filesVO) throws Exception{
		return noticeDAO.fileDetail(filesVO);
	}

	public int delete(NoticeVO noticeVO) throws Exception{
		return noticeDAO.delete(noticeVO);
		
	}
}
