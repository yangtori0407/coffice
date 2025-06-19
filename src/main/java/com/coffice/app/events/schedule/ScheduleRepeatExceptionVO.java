package com.coffice.app.events.schedule;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ScheduleRepeatExceptionVO {
	
	private Long exceptionId;
    private Long repeatId;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:SS")
    private Date exceptionDate;
    private String exceptionType;

}
