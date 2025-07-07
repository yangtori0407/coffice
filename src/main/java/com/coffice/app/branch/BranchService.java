package com.coffice.app.branch;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.coffice.app.ingredients.IngredientsVO;
import com.coffice.app.page.Pager;
import com.coffice.app.sales.SalesVO;
import com.coffice.app.users.UserVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BranchService {

	@Autowired
	private BranchDAO branchDAO;
	
	public List<BranchVO> getList(Pager pager) throws Exception {
		pager.make();
		pager.makeNum(branchDAO.getBranchTotalCount(pager));
		return branchDAO.getList(pager);
	}
	
	public List<BranchVO> getDownList() throws Exception {
		return branchDAO.getDownList();
	}
	
	public int add(BranchVO branchVO) throws Exception {
		return branchDAO.add(branchVO);
	}
	
	public List<BranchVO> notAddBranchList() throws Exception {
		return branchDAO.notAddBranchList();
	}
	
	public List<BranchMasterVO> notAddBranchMasterList() throws Exception {
		return branchDAO.notAddBranchMasterList();
	}
	
	public int branchUpdate(BranchVO branchVO) throws Exception {
		return branchDAO.branchUpdate(branchVO);
	}
	
	public BranchVO getDetail(BranchVO branchVO) throws Exception {
		return branchDAO.getDetail(branchVO);
	}
	
	public int masterAdd(BranchMasterVO branchMasterVO) throws Exception {
		return branchDAO.masterAdd(branchMasterVO);
	}
	
	public boolean nameErrorCheck(BranchMasterVO branchMasterVO, BindingResult bindingResult) throws Exception {
		boolean check = false;
		
		check = bindingResult.hasErrors();
		
		BranchMasterVO checkVO = branchDAO.nameCheck(branchMasterVO);
		if(checkVO != null) {
			check = true;
			bindingResult.rejectValue("contactNumber", "branchMasterVO.contactNumber.equal");
		}
		
		return check;
	}
	
	public boolean branchNameCheck(BranchVO branchVO, BindingResult bindingResult) throws Exception {
		boolean check = false;
		
		check = bindingResult.hasErrors();
		
		BranchVO checkVO = branchDAO.branchName(branchVO);
		if(checkVO != null) {
			check = true;
			bindingResult.rejectValue("branchName", "branchVO.branchName.equal");
		}
		
		return check;
	}
	
	public boolean branchAddressCheck(BranchVO branchVO, BindingResult bindingResult) throws Exception {
		boolean check = false;
		
		check = bindingResult.hasErrors();
		
		BranchVO checkVO = branchDAO.branchAddress(branchVO);
		if(checkVO != null) {
			check = true;
			bindingResult.rejectValue("branchAddress", "branchVO.branchAddress.equal");
		}
		
		return check;
	}
	
	public List<BranchVO> myBranch(BranchVO branchVO,Pager pager) throws Exception {
		pager.make();
		pager.makeNum(branchDAO.totalmyBranchCount(branchVO, pager));
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("branchVO", branchVO);
		map.put("pager", pager);
		return branchDAO.myBranch(branchVO, pager);
	}
	
	public Long totalBranchSales(BranchVO branchVO) throws Exception {
		return branchDAO.totalBranchSales(branchVO);
	}
	
	public List<BranchMasterVO> notRegisterBranchMaster() throws Exception {
		return branchDAO.notRegisterBranchMaster();
	}
	
	public Long totalSales() throws Exception {
		return branchDAO.totalSales();
	}
	
	public List<SalesVO> getChartList(BranchVO branchVO) throws Exception {
		return branchDAO.getChartList(branchVO);
	}
	
	public List<BranchVO> getTotalChart() throws Exception {
		return branchDAO.getTotalChart();
	}
	
	public Long registerBranch() throws Exception {
		return branchDAO.registerBranch();
	}
	
	public List<BranchVO> getTotalBranchSaleList() throws Exception {
		return branchDAO.getTotalBranchSaleList();
	}
}
