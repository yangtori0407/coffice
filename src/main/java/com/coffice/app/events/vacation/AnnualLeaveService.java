package com.coffice.app.events.vacation;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AnnualLeaveService {
	
	@Autowired
	private AnnualLeaveDAO annualLeaveDAO;
	
	public Map<String, Double> getAnnualLeaves(String userId) throws Exception {
		AnnualLeaveVO annualLeaveVO = new AnnualLeaveVO();
		annualLeaveVO.setUserId(userId);
		annualLeaveVO.setLeaveYear((long)LocalDate.now().getYear());
		
		return annualLeaveDAO.getAnnualLeaves(annualLeaveVO);
	}
	
	public int use(AnnualLeaveVO annualLeaveVO) throws Exception {
		return annualLeaveDAO.use(annualLeaveVO);
	}
	
	@Scheduled(cron = "0 0 4 1 * *")
	public void insertMonthlyLeave() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("baseDate", LocalDate.now().withDayOfMonth(1));
		annualLeaveDAO.insertMopnthlyLeave(map);
	}
	
	@Scheduled(cron = "0 0 2 1 1 *")
	public void insertYearlyLeave() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("year", LocalDate.now().getYear());
		annualLeaveDAO.insertYearlyLeave(map);
	}

}
