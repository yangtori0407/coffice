package com.coffice.app.sales;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesService {

	@Autowired
	private SalesDAO salesDAO;
	
	public List<MenuVO> menuList() throws Exception {
		return salesDAO.menuList();
	}
	
	public int profit(SalesVO salesVO) throws Exception {
		return salesDAO.profit(salesVO);
	}
	
	public int expenditure(SalesVO salesVO) throws Exception {
		return salesDAO.expenditure(salesVO);
	}
}
