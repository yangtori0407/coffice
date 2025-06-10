package com.coffice.app.ingredients;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coffice.app.page.Pager;

@Mapper
public interface IngredientsDAO {

	public List<IngredientsVO> getList(Pager pager) throws Exception;
	public IngredientsVO getDetail(IngredientsVO ingredientsVO) throws Exception;
	public Long getTotalCount(Pager pager) throws Exception;
	public int add(IngredientsVO ingredientsVO) throws Exception;
	public IngredientsVO nameCheck(IngredientsVO ingredientsVO) throws Exception;
	public int addHistory(History history) throws Exception;
}
