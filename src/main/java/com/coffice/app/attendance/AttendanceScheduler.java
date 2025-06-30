package com.coffice.app.attendance;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
public class AttendanceScheduler {
	
	@Autowired
	private AttendanceService attendanceService;
	
	@Scheduled(cron = "0 0 0 * * *")
	public void autoAbsences() throws Exception {
		System.out.println(" 스케줄러 실행됨: " + LocalDateTime.now());
		attendanceService.insertAndUpdateAbsences();
	}

}
