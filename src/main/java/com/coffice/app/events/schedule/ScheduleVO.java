package com.coffice.app.events.schedule;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.coffice.app.events.EventVO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ScheduleVO extends EventVO{
	
	private Long scheduleId;
	private Long repeatId;
	private String detail;
	private String scheduleType;
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date repeatStart;
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date repeatEnd;
	private String repeatType;

}
