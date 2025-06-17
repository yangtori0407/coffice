package com.coffice.app.notification;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationDAO {

	int add(NotificationVO notificationVO) throws Exception;

}
