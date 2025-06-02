package com.coffice.app.users;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVO {
	
	private String userId;
	private String password;
	private String name;
	private String position;
	private Date hireDate;
	private Integer hireType;
	private Date birthDate;
	private String phone;
	private String email;
	private Integer status;
	private Date resignDate;
	private String saveName;
	private String originName;
	private Integer deptId;

}
