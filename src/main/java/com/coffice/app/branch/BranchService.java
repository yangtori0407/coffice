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
}
