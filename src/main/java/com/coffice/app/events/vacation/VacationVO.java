package com.coffice.app.events.vacation;

import com.coffice.app.events.EventVO;

import lombok.Data;

@Data
public class VacationVO extends EventVO {
	
	private Long vacationId;
	private boolean status;
	private String type;
	private String approvalAuthority;
	private String aposition;
	private String aname;
	private String bposition;
	private String bname;

}
