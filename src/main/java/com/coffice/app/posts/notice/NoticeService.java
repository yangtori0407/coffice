package com.coffice.app.posts.notice;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.files.FileManager;
import com.coffice.app.ingredients.IngredientsService;
import com.coffice.app.notification.NotificationService;
import com.coffice.app.notification.NotificationVO;
import com.coffice.app.page.Pager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoticeService {

    private final IngredientsService ingredientsService;

	@Autowired
	private FileManager fileManager;

	@Value("${app.files.base}")
	private String path;

	@Autowired
	private NoticeDAO noticeDAO;
	
	@Autowired
	private NotificationService notificationService;
	

    NoticeService(IngredientsService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

	public List<NoticeVO> getList(Pager pager) throws Exception {
		pager.make();
		pager.makeNum(noticeDAO.getTotalCount(pager));
		return noticeDAO.getList(pager);
	}
	
	@Transactional
	public NoticeVO getDetail(NoticeVO noticeVO) throws Exception {
		noticeVO = noticeDAO.getDetail(noticeVO);
		return noticeVO;
	}
	@Transactional
	public int add(NoticeVO noticeVO, MultipartFile[] attaches) throws Exception {
		int result = noticeDAO.add(noticeVO);
		
		for (MultipartFile f : attaches) {
			if (f.isEmpty()) {
				continue;
			}

			String fileName = fileManager.fileSave(path.concat("notice/"), f);
			NoticeFilesVO filesVO = new NoticeFilesVO();
			filesVO.setNoticeNum(noticeVO.getNoticeNum());
			filesVO.setSaveName(fileName);
			filesVO.setOriginName(f.getOriginalFilename());

			noticeDAO.addFiles(filesVO);

		}
		
		noticeVO= noticeDAO.getDetail(noticeVO);
		//알림 보내기
		
		notificationService.sendNotice(noticeVO);
		
		return result;
	}

	public String quillUpload(MultipartFile multipartFile) throws Exception {
		String fileName = "";

		if (!multipartFile.isEmpty()) {
			fileName = fileManager.fileSave(path.concat("quill"), multipartFile);

//			NoticeFilesVO filesVO = new NoticeFilesVO();
//			filesVO.setSaveName(fileName);
//			filesVO.setOriginName(multipartFile.getOriginalFilename());
//
//			noticeDAO.quillUpload(filesVO);

		}

		return "quill\\" + fileName;
	}

	public NoticeFilesVO fileDown(NoticeFilesVO filesVO) throws Exception {
		return noticeDAO.fileDetail(filesVO);
	}

	public int delete(NoticeVO noticeVO) throws Exception {
		noticeDAO.deleteFile(noticeVO);
		return noticeDAO.delete(noticeVO);

	}

	public int update(NoticeVO noticeVO, MultipartFile[] attaches, int[] deleteFile) throws Exception {

		int result = noticeDAO.update(noticeVO);

		if (deleteFile != null)
			noticeDAO.updateFile(deleteFile);
		
		if (attaches != null) {
			for (MultipartFile f : attaches) {
				if (f.isEmpty()) {
					continue;
				}

				String fileName = fileManager.fileSave(path.concat("notice/"), f);
				NoticeFilesVO filesVO = new NoticeFilesVO();
				filesVO.setNoticeNum(noticeVO.getNoticeNum());
				filesVO.setSaveName(fileName);
				filesVO.setOriginName(f.getOriginalFilename());

				result = noticeDAO.addFiles(filesVO);
			}
		}
		return result;
	}

	public List<NoticeVO> getMainList() throws Exception{
		return noticeDAO.getMainList();
		
	}
	
	@Transactional
	public void readNoticeNotification(NoticeVO noticeVO, String userId) throws Exception{
		noticeVO.setUserId(userId);
		notificationService.checkNoticeNotification(noticeVO);
	}
}
