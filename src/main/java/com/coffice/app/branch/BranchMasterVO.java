package com.coffice.app.branch;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.coffice.app.users.RegisterGroup;
import com.coffice.app.users.UserVO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

@Data
public class BranchMasterVO {

	private UserVO userId;
	@NotEmpty(message = "사업자번호를 입력하세요", groups = RegisterGroup.class)
	private String contactNumber;
	@NotNull(message = "날짜를 입력하세요", groups = RegisterGroup.class)
	@Past(message = "이전 날짜여야 합니다", groups =RegisterGroup.class)
	private LocalDate contactDate;
	private boolean addType;
}
