package com.coffice.app.employee;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class CalendarUtils {
	
	 public static int getWorkingDays(LocalDate start, LocalDate end) {
	        int count = 0;
	        while (!start.isAfter(end)) {
	            DayOfWeek day = start.getDayOfWeek();
	            if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
	                count++;
	            }
	            start = start.plusDays(1);
	        }
	        return count;
	    }

}
