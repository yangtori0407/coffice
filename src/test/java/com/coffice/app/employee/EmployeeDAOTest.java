package com.coffice.app.employee;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeDAOTest {
	
	@Autowired
	private EmployeeDAO employeeDAO;

	@Test
	void getListTest() throws Exception {
		for(int i = 1; i <= 10; i++) {
			EmployeeVO employeeVO = new EmployeeVO();
			employeeVO.setUserId("employeeId"+ i);
			employeeVO.setName("test"+ i);
			employeeVO.setDeptName("인사팀");
			employeeVO.setPosition("대리");
			employeeVO.setEmail("employee"+i+"@code.com");
			employeeVO.setPhone("010-9876-"+ String.format("%04d", i));
			employeeVO.setHireDate(Date.valueOf("2025-06-02"));
			employeeVO.setStatus(1);

			
			employeeDAO.insertEmployees(employeeVO);
		}
	}

}
