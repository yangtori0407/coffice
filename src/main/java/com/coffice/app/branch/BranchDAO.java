package com.coffice.app.branch;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coffice.app.ingredients.IngredientsVO;
import com.coffice.app.page.Pager;
import com.coffice.app.sales.SalesVO;
import com.coffice.app.users.UserVO;


@Mapper
public interface BranchDAO {

	public List<BranchVO> getList(Pager pager) throws Exception;
	public Long getBranchTotalCount(Pager pager) throws Exception;
	public List<BranchVO> getDownList() throws Exception;
	public int add(BranchVO branchVO) throws Exception;
	public List<BranchVO> notAddBranchList() throws Exception;
	public List<BranchMasterVO> notAddBranchMasterList() throws Exception;
	public int branchUpdate(BranchVO branchVO) throws Exception;
	public BranchVO getDetail(BranchVO branchVO) throws Exception;
	public int masterAdd(BranchMasterVO branchMasterVO) throws Exception;
	public BranchMasterVO nameCheck(BranchMasterVO branchMasterVO) throws Exception;
	public List<BranchVO> myBranch(BranchVO branchVO, Pager pager) throws Exception;
	public Long totalmyBranchCount(BranchVO branchVO, Pager pager) throws Exception;
	public Long totalBranchSales(BranchVO branchVO) throws Exception;
	public Long totalSales() throws Exception;
	public List<BranchMasterVO> notRegisterBranchMaster() throws Exception;
	public List<SalesVO> getChartList(BranchVO branchVO) throws Exception;
	public List<BranchVO> getTotalChart() throws Exception;
	public List<BranchVO> getTotalBranchSaleList() throws Exception;
	public BranchVO branchName(BranchVO branchVO) throws Exception;
	public BranchVO branchAddress(BranchVO branchVO) throws Exception;
	public Long registerBranch() throws Exception;
}
