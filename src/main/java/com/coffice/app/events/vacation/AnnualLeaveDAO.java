package com.coffice.app.events.vacation;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnnualLeaveDAO {
	
	public Map<String, Double> getAnnualLeaves(AnnualLeaveVO annualLeaveVO) throws Exception;
	public int use(AnnualLeaveVO annualLeaveVO) throws Exception;
	public int insertMopnthlyLeave(Map<String, Object> map) throws Exception;
	public int insertYearlyLeave(Map<String, Object> map) throws Exception;

}
