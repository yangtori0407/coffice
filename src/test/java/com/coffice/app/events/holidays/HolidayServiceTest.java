package com.coffice.app.events.holidays;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class HolidayServiceTest {
	
	@Autowired
	private HolidayService holidayService;

	@Test
	void test() throws Exception {
		int result = holidayService.addHoliday(Calendar.getInstance().YEAR);
		
		assertNotEquals(0, result);
	}

}
