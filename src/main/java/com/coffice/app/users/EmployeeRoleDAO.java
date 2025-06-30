package com.coffice.app.users;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeRoleDAO {
	
	int insertRole (EmployeeRoleVO employeeRoleVO) throws Exception;

}
