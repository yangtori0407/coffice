package com.coffice.app.events.schedule;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.coffice.app.events.EventVO;

import lombok.Data;

@Data
public class ScheduleVO extends EventVO{
	
	private Long scheduleId;
	private Long repeatId;
	private String detail;
	private String scheduleType;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date repeatStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date repeatEnd;
	private String repeatType;

}
