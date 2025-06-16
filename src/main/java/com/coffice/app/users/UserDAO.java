package com.coffice.app.users;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserDAO {
	
	public int register(UserVO userVO) throws Exception;
	
	public UserVO detail(UserVO userVO) throws Exception;
	
	
	
	//  조직도
	public List<DepartmentVO> getDeps() throws Exception;
	public List<UserVO> getUsers(UserVO userVO) throws Exception;

}
