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
		return ingredeintsDAO.getList(pager);
	}
}
