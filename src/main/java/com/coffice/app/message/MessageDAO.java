package com.coffice.app.message;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageDAO {

	int add(MessageVO messageVO) throws Exception;

}
