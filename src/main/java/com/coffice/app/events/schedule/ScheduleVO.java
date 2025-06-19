package com.coffice.app.events.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	private Integer repeatCount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:SS")
	private Date repeatEnd;
	private String repeatType;
	private List<ScheduleRepeatExceptionVO> exceptions = new ArrayList<>();
	private boolean exception;

}
