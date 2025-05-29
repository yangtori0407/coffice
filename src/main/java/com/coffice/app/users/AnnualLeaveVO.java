package com.coffice.app.users;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnnualLeaveVO {
	
	private Integer leaveId;
	private Integer leaveYear;
	private BigDecimal totalLeave;
	private BigDecimal usedLeave;
	private Date grantDate;
	private Date expirationDate;
	private String userId;

}
