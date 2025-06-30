package com.coffice.app.events.vacation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coffice.app.events.EventUtility;
import com.coffice.app.notification.NotificationService;
import com.coffice.app.users.UserDAO;
import com.coffice.app.users.UserVO;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class VacationService {
	
	@Autowired
	private VacationDAO vacationDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private EventUtility eventUtility;
	
	@Autowired
	private AnnualLeaveService annualLeaveService;
	
	@Autowired
	private NotificationService notificationService;
	
	public List<UserVO> getDepsUsers(UserVO userVO) throws Exception {
		return vacationDAO.getDepsUsers(userVO);
	}
	
	@Transactional
	public int applyForLeave(VacationVO vacationVO, Authentication authentication) throws Exception {
		UserVO userVO = (UserVO)authentication.getPrincipal();
		vacationVO.setUserId(userVO.getUserId());
		int result = vacationDAO.applyForLeave(vacationVO);
		String mes = userVO.getPosition() + " " + userVO.getName() + "의 휴가 신청";
		notificationService.sendVaction(mes, vacationVO.getApprovalAuthority());
		return result;
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
	
	@Transactional
	public int approve(VacationVO vacationVO, Authentication authentication) throws Exception {
		vacationVO = vacationDAO.getOne(vacationVO);
		UserVO userVO = (UserVO)authentication.getPrincipal();
		vacationVO.setApprovalAuthority(userVO.getUserId());
		
		LocalDateTime start = vacationVO.getStartTime();
		LocalDateTime end = vacationVO.getEndTime();
		List<LocalDate> holidays = eventUtility.getHolidays();

		Double daysUsed = eventUtility.calculateAnnualLeaveDays(start, end, holidays);
		vacationVO.setDaysUsed(daysUsed);
		
		int result = vacationDAO.approve(vacationVO);
		log.info("approve : {}", result);
		
		AnnualLeaveVO annualLeaveVO = new AnnualLeaveVO();
		annualLeaveVO.setLeaveYear((long)start.getYear());
		annualLeaveVO.setUsedDate(start);
		annualLeaveVO.setUserId(vacationVO.getUserId());
		annualLeaveVO.setVacationId(vacationVO.getVacationId());
		annualLeaveVO.setUsedLeave(daysUsed);
		
		result = annualLeaveService.use(annualLeaveVO);
		log.info("use : {}", result);
		
		String mes = "휴가 신청이 승인됨";
		notificationService.sendVaction(mes, vacationVO.getUserId());
		
		return result;
	}
	
	@Transactional
	public int reject(VacationVO vacationVO, Authentication authentication) throws Exception {
		vacationVO = vacationDAO.getOne(vacationVO);
		UserVO userVO = (UserVO)authentication.getPrincipal();
		vacationVO.setApprovalAuthority(userVO.getUserId());
		
		int result = vacationDAO.reject(vacationVO);
		log.info("reject : {}", result);
		
		String mes = "휴가 신청이 거절됨";
		notificationService.sendVaction(mes, vacationVO.getUserId());
		
		return result;
	}
	
	public List<VacationVO> getList(UserVO userVO) throws Exception {
		return vacationDAO.getList(userVO);
	}
	
	public int updateApply(VacationVO vacationVO) throws Exception {
		return vacationDAO.updateApply(vacationVO);
	}

}
