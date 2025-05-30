package com.coffice.app.branch;

import java.sql.Date;

import lombok.Data;

@Data
public class BranchMasterVO {

	private String userId;
	private String contactNumber;
	private Date contactDate;
}
