package com.coffice.app.documents;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.documents.attachments.AttachmentVO;
import com.coffice.app.documents.forms.FormVO;
import com.coffice.app.files.FileManager;
import com.coffice.app.pagers.Pager;
import com.coffice.app.users.UserVO;

@Service
public class DocumentService {
	
	
	@Autowired
	private DocumentDAO documentDAO;
	
	@Autowired
	private FileManager fileManager;
	
	
	//
	public FormVO formDetail(FormVO formVO) throws Exception {
		
		return documentDAO.formDetail(formVO);
	}
	
	
	//
	public List<UserVO> getUsers() throws Exception {
				
		List<UserVO> list = documentDAO.getUsers();
		
		return list;
	}
	
	
	//
	public List<FormVO> getForms() throws Exception {
				
		List<FormVO> list = documentDAO.getForms(); 
		
		return list;
	}
	
	
	//
	public List<DocumentVO> getList(Pager pager) throws Exception {
		
		//pager.make(documentDAO.getTotalCount(pager));
		//pager.makeNum();
		List<DocumentVO> list = documentDAO.getList(pager); 
		
		return list;
	}
	
	
	//
	public DocumentVO getDetail(DocumentVO documentVO) throws Exception {
		
				
		return documentDAO.getDetail(documentVO);
	}
	
	
	//
	public int add(DocumentVO documentVO, MultipartFile [] multipartFiles) throws Exception {
		
		// 문서 입력
		int result = documentDAO.add(documentVO);
		
		// 파일 세이브
		if (multipartFiles != null) {
			for(MultipartFile f : multipartFiles) {
				
				if(f.isEmpty()) {
					continue;
				}
				
				String fileName = fileManager.fileSave("패스 넣어야함", f);
				
				// 파일명 입력
				AttachmentVO vo = new AttachmentVO();
				vo.setDocumentId(documentVO.getDocumentId());
				vo.setOriginName(f.getOriginalFilename());
				vo.setSaveName(fileName);
				
				result = documentDAO.addFile(vo);
				
			}
			
			
		}
		
		
		return result;
	}
	
	
	//
	public AttachmentVO getFileDetail(AttachmentVO attachmentVO) throws Exception {
		
		
		return documentDAO.getFileDetail(attachmentVO);
	}
	
	
	
	
	

}
