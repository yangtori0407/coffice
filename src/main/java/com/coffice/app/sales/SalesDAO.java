package com.coffice.app.sales;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coffice.app.ingredients.IngredientsVO;

@Mapper
public interface SalesDAO {
	public List<MenuVO> menuList() throws Exception;
	public int profit(SalesVO salesVO) throws Exception;
	public int expenditure(SalesVO salesVO) throws Exception;
	public int addMenu(MenuVO menuVO) throws Exception;
	public MenuVO nameCheck(MenuVO menuVO) throws Exception;
}
