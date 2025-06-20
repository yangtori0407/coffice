package com.coffice.app.events.vacation;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coffice.app.users.UserVO;

@Mapper
public interface VacationDAO {
	
	public List<UserVO> getDepsUsers(UserVO userVO) throws Exception;

}
