package com.coffice.app.events.vacation;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coffice.app.users.UserVO;

@Mapper
public interface VacationDAO {
	
	public List<UserVO> getDepsUsers(UserVO userVO) throws Exception;
	public int applyForLeave(VacationVO vacationVO) throws Exception;
	public List<VacationVO> getApplyList(UserVO userVO) throws Exception;
	public List<VacationVO> getAcceptList(UserVO userVO) throws Exception;
	public VacationVO getOne(VacationVO vacationVO) throws Exception;
	public int approve(VacationVO vacationVO) throws Exception;

}
