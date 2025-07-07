package com.coffice.app.employee;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.attendance.AttendanceDAO;
import com.coffice.app.files.FileManager;
import com.coffice.app.page.Pager;
import com.coffice.app.posts.board.BoardVO;


@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeDAO employeeDAO;
	
	@Autowired
	private FileManager fileManager;
	
	@Value("${app.files.base}")
	private String path;
	
	@Autowired
	private AttendanceDAO attendanceDAO;
	
	public List<EmployeeVO> getAllEmployees(Pager pager) throws Exception {
		pager.make();
		pager.makeNum(employeeDAO.getTotalCount(pager));
		List<EmployeeVO> list = employeeDAO.getAllEmployees(pager);
		
		return list;
	}
	
	public EmployeeVO getEmployeeById(String userId) throws Exception {
		return employeeDAO.getEmployeeById(userId);
	}
	
	public int updateEmployee(EmployeeVO employeeVO, MultipartFile file) throws Exception {
	    EmployeeVO origin = employeeDAO.getEmployeeById(employeeVO.getUserId());

	    boolean isChanged = false;

	   
	    if (!file.isEmpty()) {
	        String fileName = fileManager.fileSave(path.concat("profile/"), file);
	        employeeVO.setSaveName(fileName);
	        employeeVO.setOriginName(file.getOriginalFilename());
	        isChanged = true;
	    } else {
	        employeeVO.setSaveName(origin.getSaveName());
	        employeeVO.setOriginName(origin.getOriginName());
	    }
	    
	    if (employeeVO.getName() == null || employeeVO.getName().isBlank()) {
	        employeeVO.setName(origin.getName());
	    } else if (!employeeVO.getName().equals(origin.getName())) {
	        isChanged = true;
	    }

	    
	    if (employeeVO.getHireDate() == null) {
	        employeeVO.setHireDate(origin.getHireDate());
	    } else if (!employeeVO.getHireDate().equals(origin.getHireDate())) {
	        isChanged = true;
	    }

	 
	    if (employeeVO.getDeptId() == null) {
	        
	        employeeVO.setDeptId(origin.getDeptId());
	    } else if (!employeeVO.getDeptId().equals(origin.getDeptId())) {
	        
	        isChanged = true;
	    }
	    
	    if (employeeVO.getStatus() == null) {
	        employeeVO.setStatus(origin.getStatus());
	    } else if (!employeeVO.getStatus().equals(origin.getStatus())) {
	        isChanged = true;
	    }

	    if (employeeVO.getHireType() == null) {
	        employeeVO.setHireType(origin.getHireType());
	    } else if (!employeeVO.getHireType().equals(origin.getHireType())) {
	        isChanged = true;
	    }

	    
	    //System.out.println("넘어온 deptId: " + employeeVO.getDeptId());
	    //System.out.println("기존 deptId: " + origin.getDeptId());

	    
	    if (employeeVO.getPosition() == null || employeeVO.getPosition().isBlank()) {
	        employeeVO.setPosition(origin.getPosition());
	    } else if (!employeeVO.getPosition().equals(origin.getPosition())) {
	        isChanged = true;
	    }

	    
	    if (employeeVO.getEmail() == null || employeeVO.getEmail().isBlank()) {
	        employeeVO.setEmail(origin.getEmail());
	    } else if (!employeeVO.getEmail().equals(origin.getEmail())) {
	        isChanged = true;
	    }
	    
	    if (employeeVO.getBirthDate() == null) {
	        employeeVO.setBirthDate(origin.getBirthDate());
	    } else if (!employeeVO.getBirthDate().equals(origin.getBirthDate())) {
	        isChanged = true;
	    }
	    
	    if (employeeVO.getResignDate() == null) {
	        employeeVO.setResignDate(origin.getResignDate());
	    } else if (!employeeVO.getResignDate().equals(origin.getResignDate())) {
	        isChanged = true;
	    }

	   
	    if (employeeVO.getPhone() == null || employeeVO.getPhone().isBlank()) {
	        employeeVO.setPhone(origin.getPhone());
	    } else if (!employeeVO.getPhone().equals(origin.getPhone())) {
	        isChanged = true;
	    }

	    if (!isChanged) {
	        return 0;
	    }

	    return employeeDAO.updateEmployee(employeeVO);
	}
	
	public List<EmployeeVO> getEmployeesAttendancePercentage(Pager pager, LocalDate startDate, LocalDate endDate)throws Exception{
		int totalWorkingDays = CalendarUtils.getWorkingDays(startDate, endDate);
		
		pager.make();
		pager.makeNum(employeeDAO.getTotalCount(pager));
		List<EmployeeVO> employeeList = employeeDAO.getAllEmployees(pager);
		
		for(EmployeeVO employee : employeeList) {
			int attendanceCount = attendanceDAO.getAttendanceForMonth(employee.getUserId(), startDate, endDate);
			int percent = totalWorkingDays == 0 ? 0 : Math.round((attendanceCount * 100f) / totalWorkingDays);
			employee.setAttendancePercent(percent);
		}
		
		return employeeList;
	}
}
