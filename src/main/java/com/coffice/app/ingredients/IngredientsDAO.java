package com.coffice.app.ingredients;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coffice.app.page.Pager;

@Mapper
public interface IngredientsDAO {

	public List<IngredientsVO> getList(Pager pager) throws Exception;
}
