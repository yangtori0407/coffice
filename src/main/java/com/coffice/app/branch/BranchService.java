package com.coffice.app.branch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BranchService {

	@Autowired
	private BranchDAO branchDAO;
	
	public List<BranchVO> getList() throws Exception {
		return branchDAO.getList();
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
}
