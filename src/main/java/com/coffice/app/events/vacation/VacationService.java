package com.coffice.app.events.vacation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffice.app.users.UserVO;

@Service
public class VacationService {
	
	@Autowired
	private VacationDAO vacationDAO;
	
	public List<UserVO> getDepsUsers(UserVO userVO) throws Exception {
		return vacationDAO.getDepsUsers(userVO);
	}

}
