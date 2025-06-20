package com.coffice.app.notification;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.coffice.app.posts.notice.NoticeVO;
import com.coffice.app.users.UserVO;

@Mapper
public interface NotificationDAO {

	int add(NotificationVO notificationVO) throws Exception;

	int addNoticeCheck(Map<String, Object> info) throws Exception;

	List<UserVO> getUserList(String userId) throws Exception;

	List<NotificationVO> getNotification(String userId) throws Exception;

}
