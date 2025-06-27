package com.coffice.app.events.vacation;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

}
