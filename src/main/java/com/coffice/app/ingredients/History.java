package com.coffice.app.ingredients;

import java.sql.Date;
import java.sql.Timestamp;

import com.coffice.app.users.UserVO;

import lombok.Data;

@Data
public class History {

	private Integer historyId;
	private boolean receive;
	private Integer number;
	private String userId;
	private Integer ingredientsID;
	private UserVO userVO;
	private Timestamp registrationDate;
}
