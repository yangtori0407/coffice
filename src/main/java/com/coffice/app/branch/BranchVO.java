package com.coffice.app.branch;

import java.util.List;

import com.coffice.app.sales.SalesVO;
import com.coffice.app.users.UserVO;

import lombok.Data;

@Data
public class BranchVO {

	private Integer branchId;
	private String branchName;
	private String branchAddress;
	private String branchPostcode;
	private boolean branchStatus;
	private String userId;
	private UserVO userVO;
	private BranchMasterVO branchMasterVO;
	private List<SalesVO> salesVO;
}
