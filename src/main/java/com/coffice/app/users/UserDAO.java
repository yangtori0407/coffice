package com.coffice.app.users;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserDAO {
	
	public int register(UserVO userVO) throws Exception;
	
	public UserVO detail(UserVO userVO) throws Exception;

}
