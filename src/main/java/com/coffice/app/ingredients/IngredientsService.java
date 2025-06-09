package com.coffice.app.ingredients;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffice.app.page.Pager;

@Service
public class IngredientsService {

	@Autowired
	private IngredientsDAO ingredeintsDAO;
	
	public List<IngredientsVO> getList(Pager pager) throws Exception {
		pager.make();
		pager.makeNum(ingredeintsDAO.getTotalCount(pager));
		return ingredeintsDAO.getList(pager);
	}
	
	public IngredientsVO getDetail(IngredientsVO ingredientsVO) throws Exception {
		return ingredeintsDAO.getDetail(ingredientsVO);
	}
}
