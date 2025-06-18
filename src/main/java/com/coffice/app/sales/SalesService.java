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
}
