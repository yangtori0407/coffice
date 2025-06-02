package com.coffice.app.events.schedule;

import com.coffice.app.events.EventVO;

import lombok.Data;

@Data
public class ScheduleVO extends EventVO{
	
	private Long scheduleId;
	private Long repeatId;
	private String detail;
	private String scheduleType;

}
