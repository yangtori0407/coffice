package com.coffice.app.events.holidays;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class HolidayVO {
	
	private String dateKind;
	private String dateName;
	private String isHoliday;
//	@DateTimeFormat(pattern = "yyyyMMdd")
	private String locdate;
	private String seq;

}
