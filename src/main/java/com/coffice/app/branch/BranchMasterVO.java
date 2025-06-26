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
	private String contactNumber;
	private LocalDate contactDate;
	private boolean addType;
}
