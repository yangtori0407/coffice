package com.coffice.app.users;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleDAO {
	
	Long findRoleIdByName (String roleName) throws Exception;
	

}
