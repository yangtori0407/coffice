package com.coffice.app.branch;

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
}
