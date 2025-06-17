package com.coffice.app.users;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserDAO {
	
	public int register(UserVO userVO) throws Exception;
	
	public UserVO detail(UserVO userVO) throws Exception;
	
	public UserVO findByEmail(String email) throws Exception;
	
	public UserVO checkIdEmail(String email, String userId) throws Exception;
	
	public int updatePassword(UserVO userVO) throws Exception;
	
	public String checkPassword(String userId) throws Exception;
	
	public boolean existUserId(String userId) throws Exception;

}
