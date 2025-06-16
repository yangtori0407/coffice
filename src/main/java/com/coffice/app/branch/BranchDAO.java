package com.coffice.app.branch;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coffice.app.page.Pager;
import com.coffice.app.users.UserVO;


@Mapper
public interface BranchDAO {

	public List<BranchVO> getList(Pager pager) throws Exception;
	public List<BranchVO> getDownList() throws Exception;
	public int add(BranchVO branchVO) throws Exception;
	public List<BranchVO> notAddBranchList() throws Exception;
	public List<BranchMasterVO> notAddBranchMasterList() throws Exception;
	public int branchUpdate(BranchVO branchVO) throws Exception;
	public BranchVO getDetail(BranchVO branchVO) throws Exception;
	public int masterAdd(BranchMasterVO branchMasterVO) throws Exception;
	public List<BranchVO> myBranch(BranchVO branchVO) throws Exception;
	public Long totalSales(BranchVO branchVO) throws Exception;
	public List<BranchMasterVO> notRegisterBranchMaster() throws Exception;
}
