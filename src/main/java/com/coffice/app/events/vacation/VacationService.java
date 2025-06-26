package com.coffice.app.events.vacation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffice.app.users.UserDAO;
import com.coffice.app.users.UserVO;

@Service
public class VacationService {
	
	@Autowired
	private VacationDAO vacationDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	public List<UserVO> getDepsUsers(UserVO userVO) throws Exception {
		return vacationDAO.getDepsUsers(userVO);
	}
	
	public int applyForLeave(VacationVO vacationVO) throws Exception {
		return vacationDAO.applyForLeave(vacationVO);
	}
	
	public List<VacationVO> getApplyList(UserVO userVO) throws Exception {
		return vacationDAO.getApplyList(userVO);
	}
	
	public List<VacationVO> getAcceptList(UserVO userVO) throws Exception {
		return vacationDAO.getAcceptList(userVO);
	}
	
	public Map<String, Object> getOne(VacationVO vacationVO) throws Exception {
		Map<String, Object> map = new HashMap<>();
		vacationVO = vacationDAO.getOne(vacationVO);
		UserVO userVO = new UserVO();
		userVO.setUserId(vacationVO.getUserId());
		UserVO applier = userDAO.detail(userVO);
		userVO.setUserId(vacationVO.getApprovalAuthority());
		UserVO accepter = userDAO.detail(userVO);
		map.put("accepter", accepter);
		map.put("applier", applier);
		map.put("vacationVO", vacationVO);
		return map;
	}
	
	public int approve(VacationVO vacationVO) throws Exception {
		return vacationDAO.approve(vacationVO);
	}
	
	public List<VacationVO> getList(UserVO userVO) throws Exception {
		return vacationDAO.getList(userVO);
	}
	
	public int updateApply(VacationVO vacationVO) throws Exception {
		return vacationDAO.updateApply(vacationVO);
	}

}
