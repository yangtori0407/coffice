package com.coffice.app.events.vacation;

import com.coffice.app.events.EventVO;

import lombok.Data;

@Data
public class VacationVO extends EventVO {
	
	private Long vacationId;
	private String status;
	private String type;
	private String approvalAuthority;

}
