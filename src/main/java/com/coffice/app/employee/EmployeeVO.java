package com.coffice.app.employee;





import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeVO {
	
	    private String userId;
	    private String name;
	    private Integer deptId;
	    private String deptName;
	    private String position;
	    private String email;
	    private String phone;
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date hireDate;
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date birthDate;
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date resignDate;
	    private Integer hireType;
	    private Integer status;
	    private String saveName;
	    private String originName;
	    
	    private String kind;
	    private String search;
	    
	    private int attendancePercent;

}
