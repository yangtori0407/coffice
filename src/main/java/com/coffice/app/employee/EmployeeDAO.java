package com.coffice.app.employee;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.coffice.app.page.Pager;

@Mapper
public interface EmployeeDAO {
	
	public int insertEmployees (EmployeeVO employeeVO) throws Exception;
	
	public List<EmployeeVO> getAllEmployees(Pager pager) throws Exception;
	
	public Long getTotalCount(Pager pager) throws Exception;
	
	public EmployeeVO getEmployeeById(String userId) throws Exception;
	
	public int updateEmployee(EmployeeVO employeeVO) throws Exception;
	
}
	
	
