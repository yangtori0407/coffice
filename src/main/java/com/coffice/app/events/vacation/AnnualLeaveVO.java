package com.coffice.app.events.vacation;


import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AnnualLeaveVO {
	
	private Long leaveId;
	private Long leaveYear;
	private Double grantLeave;
	private Double usedLeave;
	private LocalDate grantDate;
	private LocalDate expirationDate;
	private LocalDateTime usedDate;
	private String userId;
	private String type;
	private Long vacationId;

}
