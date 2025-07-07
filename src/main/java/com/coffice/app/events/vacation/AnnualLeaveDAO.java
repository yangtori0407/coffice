package com.coffice.app.events.vacation;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.coffice.app.users.UserVO;

@Mapper
public interface AnnualLeaveDAO {
	
	public Map<String, Double> getAnnualLeaves(AnnualLeaveVO annualLeaveVO) throws Exception;
	public int use(AnnualLeaveVO annualLeaveVO) throws Exception;
	public int insertYearlyLeave(Map<String, Object> map) throws Exception;
	public List<AnnualLeaveVO> getList(UserVO userVO) throws Exception;
	public int createEmpQualifiedTemp(Map<String, Object> map) throws Exception;
	public int insertMonthlyLeave(Map<String, Object> map) throws Exception;

}
