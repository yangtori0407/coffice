package com.coffice.app.events;

import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coffice.app.events.holidays.HolidayService;
import com.coffice.app.events.holidays.HolidayVO;

@Component
public class EventUtility {
    
    @Autowired
    private HolidayService holidayService;

    public Double calculateAnnualLeaveDays(LocalDateTime start, LocalDateTime end, List<LocalDate> holidays) {
        // 기준 근무시간: 09:00~18:00 (총 8시간), 휴게시간: 12:00~13:00
        LocalTime workStart = LocalTime.of(9, 0);
        LocalTime workEnd = LocalTime.of(18, 0);
        LocalTime lunchStart = LocalTime.of(12, 0);
        LocalTime lunchEnd = LocalTime.of(13, 0);

        double totalLeaveHours = 0;
        LocalDate current = start.toLocalDate();
        LocalDate endDate = end.toLocalDate();

        while (!current.isAfter(endDate)) {
            boolean isWeekend = current.getDayOfWeek() == DayOfWeek.SATURDAY || current.getDayOfWeek() == DayOfWeek.SUNDAY;
            boolean isHoliday = holidays.contains(current);
            if (isWeekend || isHoliday) {
                current = current.plusDays(1);
                continue;
            }

            // 일자별 시작/종료 시간 계산
            LocalDateTime dayStart = current.equals(start.toLocalDate()) ? start : LocalDateTime.of(current, workStart);
            LocalDateTime dayEnd = current.equals(end.toLocalDate()) ? end : LocalDateTime.of(current, workEnd);

            Duration duration = Duration.between(dayStart, dayEnd);
            double hours = duration.toMinutes() / 60.0;

            // 휴게시간 제거
            if (!dayStart.toLocalTime().isAfter(lunchEnd) && !dayEnd.toLocalTime().isBefore(lunchStart)) {
                hours -= 1;
            }

            // 최소 0으로 보정
            totalLeaveHours += Math.max(hours, 0);
            current = current.plusDays(1);
        }

        return totalLeaveHours / 8.0; // 8시간 = 1일 연차 기준
    }

    
    public List<LocalDate> getHolidays() throws Exception {
    	List<HolidayVO> vos = holidayService.getHolidays();
    	List<LocalDate> holidays = new ArrayList<>();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    	for(HolidayVO vo : vos) {
    		LocalDate date = LocalDate.parse(vo.getLocdate(), formatter);
    		holidays.add(date);
    	}
    	return holidays;
    }
    
}

