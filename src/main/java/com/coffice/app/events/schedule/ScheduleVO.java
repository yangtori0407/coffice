package com.coffice.app.events.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul", pattern = "yyyy-MM-dd'T'HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:SS")
	private LocalDateTime repeatEnd;
	private String repeatType;
	private List<ScheduleRepeatExceptionVO> exceptions = new ArrayList<>();
	private boolean exception;

}
