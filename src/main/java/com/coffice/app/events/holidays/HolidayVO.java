package com.coffice.app.events.holidays;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class HolidayVO {
	
	private String dateKind;
	private String dateName;
	private String isHoliday;
	private String locdate;
	private LocalDate localDate;
	private String seq;

}
