package com.coffice.app.attendance;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AttendanceScheduler {
	
	private AttendanceService attendanceService;
	
	@Scheduled(cron = "0 0 0 * * *")
	public void autoAbsences() throws Exception {
		attendanceService.insertAndUpdateAbsences();
	}

}
