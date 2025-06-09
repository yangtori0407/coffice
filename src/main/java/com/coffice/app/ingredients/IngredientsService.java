package com.coffice.app.ingredients;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

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
	
	public int add(IngredientsVO ingredientsVO) throws Exception {
		return ingredeintsDAO.add(ingredientsVO);
	}
	
	public boolean nameErrorCheck(IngredientsVO ingredientsVO, BindingResult bindingResult) throws Exception {
		boolean check = false;
		check = bindingResult.hasErrors();
		
		IngredientsVO checkVO = ingredeintsDAO.getDetail(ingredientsVO);
		if(checkVO != null) {
			check = true;
			bindingResult.rejectValue("ingredientsID", "ingredientsVO.ingredientsID.equal");
		}
		
		return check;
	}
}
