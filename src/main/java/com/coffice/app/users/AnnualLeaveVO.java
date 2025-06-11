package com.coffice.app.users;


import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnnualLeaveVO {
	
	private Long leaveId;
	private Long leaveYear;
	private Double totalLeave;
	private Double usedLeave;
	private Date grantDate;
	private Date expirationDate;
	private String userId;

}
