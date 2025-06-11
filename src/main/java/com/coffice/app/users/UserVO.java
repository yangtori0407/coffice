package com.coffice.app.users;

import java.sql.Date;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.users.RegisterGroup;
import com.coffice.app.users.UpdateGroup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVO implements UserDetails{
	
	@NotBlank(message = "사원번호는 필수사항입니다", groups = RegisterGroup.class)
	private String userId;
	@Size(min=8)
	@NotBlank(groups = RegisterGroup.class)
	private String password;
	//db 저장 x
	private String passwordCheck;
	@NotBlank(groups = {RegisterGroup.class, UpdateGroup.class})
	private String name;
	private String position;
	private Date hireDate;
	private Integer hireType;
	@Past(groups =RegisterGroup.class)
	private Date birthDate;
	@NotBlank(groups = {RegisterGroup.class, UpdateGroup.class})
	private String phone;
	@Email(groups = {RegisterGroup.class, UpdateGroup.class})
	private String email;
	private Integer status;
	private Date resignDate;
	private String saveName;
	private String originName;
	private Integer deptId;
	
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
