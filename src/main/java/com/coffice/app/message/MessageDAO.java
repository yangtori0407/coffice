package com.coffice.app.message;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.coffice.app.users.UserVO;

@Mapper
public interface MessageDAO {

	int add(MessageVO messageVO) throws Exception;

	int addEmailUser(Map<String, Object> info) throws Exception;

	List<MessageVO> getSendMessage(Map<String, Object> info) throws Exception;

	List<MessageVO> getReceiveMessage(Map<String, Object> info) throws Exception;

	MessageVO detail(MessageVO messageVO) throws Exception;

	int receiveDelete(Map<String, Object> info) throws Exception;

	int sendDelete(Map<String, Object> info) throws Exception;

	UserVO getUserInfo(String userId) throws Exception;

	long getSendTotal(Map<String, Object> info) throws Exception;

	long getReceiveTotal(Map<String, Object> info) throws Exception;

	MessageVO getCheck(Long messageNum) throws Exception;

}
