package com.coffice.app.users;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.users.RegisterGroup;
import com.coffice.app.users.UpdateGroup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVO implements UserDetails{
	
	private String userId;
	@Size(min=8, message = "비밀번호 8자리 이상 필수사항입니다", groups= UpdateGroup.class)
	@NotBlank(message = "필수사항입니다",groups = RegisterGroup.class)
	private String password;
	//db 저장 x
	@NotBlank(message = "필수사항입니다",groups = {RegisterGroup.class, UpdateGroup.class})
	private String passwordCheck;
	@NotBlank(message = "필수사항입니다",groups = RegisterGroup.class)
	private String name;
	private String position;
	private Date hireDate;
	private Integer hireType;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="생년월일은 필수입니다", groups = RegisterGroup.class)
	@Past(message = "생년월일은 오늘 이전 날짜여야 합니다", groups =RegisterGroup.class)
	private LocalDate birthDate;
	@NotBlank(message = "필수사항입니다",groups = {RegisterGroup.class, UpdateGroup.class})
	private String phone;
	@Email(message = "이메일 형식이 올바르지 않습니다",groups = {RegisterGroup.class, UpdateGroup.class})
	@NotBlank(message = "필수사항입니다", groups= RegisterGroup.class)
	private String email;
	private Integer status;
	private Date resignDate;
	private String saveName;
	private String originName;
	private Integer deptId;
	
	private String deptName;
	private MultipartFile file;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return null;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userId;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return UserDetails.super.isAccountNonExpired();
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return UserDetails.super.isAccountNonLocked();
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return UserDetails.super.isCredentialsNonExpired();
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return UserDetails.super.isEnabled();
	}
	
	

	
	
}
