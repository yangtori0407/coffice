package com.coffice.app.posts.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.files.FileManager;
import com.coffice.app.page.Pager;
import com.coffice.app.posts.QuillFileVO;

@Service
public class BoardSerivce {
	
	@Autowired
	private BoardDAO boardDAO;
	
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

	public BoardVO getDetail(BoardVO boardVO) throws Exception{
		return boardDAO.getDetail(boardVO);
	}

	public String quillUpload(MultipartFile file) throws Exception{
		String fileName = "";
		if(!file.isEmpty()) {
			fileName = fileManager.fileSave(path.concat("quill"), file);
			
			QuillFileVO quillFileVO = new QuillFileVO();
			quillFileVO.setSaveName(fileName);
			quillFileVO.setOriginName(file.getOriginalFilename());
			
			boardDAO.quillUpload(quillFileVO);
		}
		
		return "\\quill\\" + fileName;
	}
}
