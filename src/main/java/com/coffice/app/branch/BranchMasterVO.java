package com.coffice.app.branch;

import java.sql.Date;
import java.util.List;

import com.coffice.app.users.UserVO;

import lombok.Data;

@Data
public class BranchMasterVO {

	private UserVO userId;
	private String contactNumber;
	private Date contactDate;
	private boolean addType;
}
