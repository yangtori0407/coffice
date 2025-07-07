package com.coffice.app.events;

import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coffice.app.events.holidays.HolidayService;
import com.coffice.app.events.holidays.HolidayVO;
import com.coffice.app.events.schedule.ScheduleRepeatExceptionVO;
import com.coffice.app.events.schedule.ScheduleVO;
import com.coffice.app.events.vacation.VacationVO;

@Component
public class EventUtility {
    
    @Autowired
    private HolidayService holidayService;
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    
    public static CalendarEventDTO fromHoliday(HolidayVO vo) {
        CalendarEventDTO dto = new CalendarEventDTO();
        LocalDate date = LocalDate.parse(vo.getLocdate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        dto.setTitle(vo.getDateName());
        dto.setStart(date.atStartOfDay().format(FORMATTER));
        dto.setAllDay(true);
        dto.setColor("#ee0000");
        dto.setEditable(false);
        return dto;
    }

    public static CalendarEventDTO fromSchedule(ScheduleVO vo, String currentUserId, String pcolor, String gcolor) {
        CalendarEventDTO dto = new CalendarEventDTO();
        dto.setId(String.valueOf(vo.getScheduleId()));
        dto.setTitle(vo.getDetail());
        dto.setStart(vo.getStartTime().format(FORMATTER));
        dto.setEnd(vo.getEndTime().format(FORMATTER));
        dto.setAllDay(false);
        dto.setEditable(false);

        // 색상 구분
        dto.setColor(currentUserId.equals(vo.getUserId()) ? pcolor : gcolor);

        // 반복일정
        if (vo.getRepeatType() != null) {
            dto.setGroupId(String.valueOf(vo.getRepeatId()));
            Map<String, Object> rrule = new HashMap<>();
            rrule.put("freq", vo.getRepeatType());
            rrule.put("dtstart", vo.getStartTime().format(FORMATTER));
            if (vo.getRepeatEnd() != null) {
                rrule.put("until", vo.getRepeatEnd().format(FORMATTER));
            }
            if (vo.getRepeatCount() != null) {
                rrule.put("count", vo.getRepeatCount());
            }
            dto.setRrule(rrule);

            // 예외일 처리
            if (vo.getExceptions() != null) {
                List<String> exdates = new ArrayList<>();
                for (ScheduleRepeatExceptionVO e : vo.getExceptions()) {
                    if (e.getExceptionDate() != null) {
                        LocalDateTime ex = e.getExceptionDate()
                                			.toInstant()
                        					.atZone(ZoneId.systemDefault())
                        					.toLocalDateTime();
                        exdates.add(ex.format(FORMATTER));
                    }
                }
                dto.setExdate(exdates);
            }

            // duration 설정
            dto.setDuration(calculateDuration(vo.getStartTime(), vo.getEndTime()));
        }

        // 확장 속성
        dto.getExtendedProps().put("type", vo.getScheduleType());
        dto.getExtendedProps().put("userId", vo.getUserId());

        return dto;
    }

    public static CalendarEventDTO fromVacation(VacationVO vo, String currentUserId, String pcolor, String gcolor) {
        CalendarEventDTO dto = new CalendarEventDTO();
        dto.setId(String.valueOf(vo.getVacationId()));
        dto.setTitle(vo.getAposition() + " " + vo.getAname());
        dto.setStart(vo.getStartTime().format(FORMATTER));
        dto.setEnd(vo.getEndTime().format(FORMATTER));
        dto.setAllDay(false);
        dto.setEditable(false);
        dto.setColor(currentUserId.equals(vo.getUserId()) ? pcolor : gcolor);
        dto.getExtendedProps().put("status", vo.getStatus());
        return dto;
    }

    private static String calculateDuration(LocalDateTime start, LocalDateTime end) {
        Duration d = Duration.between(start, end);
        long hours = d.toHours();
        long minutes = d.toMinutesPart();
        long seconds = d.toSecondsPart();

        return String.format("PT%02dH%02dM%02dS", hours, minutes, seconds);
    }

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
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	for(HolidayVO vo : vos) {
    		LocalDate date = LocalDate.parse(vo.getLocdate(), formatter);
    		holidays.add(date);
    	}
    	return holidays;
    }
    
}

