package com.coffice.app.sales;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SalesDAO {
	public List<MenuVO> menuList() throws Exception;
}
