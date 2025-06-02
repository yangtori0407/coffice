package com.coffice.app.branch;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BranchDAO {

	public List<BranchVO> getList() throws Exception;
	public int add(BranchVO branchVO) throws Exception;
}
