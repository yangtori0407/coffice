package com.coffice.app.sales;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.files.FileManager;
import com.coffice.app.ingredients.IngredientsVO;

@Service
public class SalesService {

	@Autowired
	private SalesDAO salesDAO;
	@Autowired
	private FileManager fileManager;
	@Value("D:/workspace2/coffice/upload/menu/")
	private String path;
	
	public List<MenuVO> menuList() throws Exception {
		return salesDAO.menuList();
	}
	
	public int profit(SalesVO salesVO) throws Exception {
		return salesDAO.profit(salesVO);
	}
	
	public int expenditure(SalesVO salesVO) throws Exception {
		return salesDAO.expenditure(salesVO);
	}
	
	public int addMenu(MenuVO menuVO, MultipartFile multipartFile) throws Exception {
		if(multipartFile!=null && !multipartFile.isEmpty()) {
			String filename = fileManager.fileSave(path, multipartFile);
			menuVO.setSaveName(filename);
			menuVO.setOriginName(multipartFile.getOriginalFilename());
		}
		return salesDAO.addMenu(menuVO);
	}
	
	public boolean nameErrorCheck(MenuVO menuVO, BindingResult bindingResult) throws Exception {
		
		MenuVO checkVO = salesDAO.nameCheck(menuVO);
		if(checkVO != null) {
			return true;
		}
		
		return false;
	}
}
