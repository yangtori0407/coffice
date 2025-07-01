package com.coffice.app.users;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.users.RegisterGroup;
import com.coffice.app.users.UpdateGroup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVO implements UserDetails{
	
	private String userId;
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "비밀번호는 8자리 이상이며, 영문자와 숫자를 포함해야 합니다.")
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
	
	private List<RoleVO> roles;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<GrantedAuthority> ar = new ArrayList<>();
		 if (this.roles != null) {
			for(RoleVO roleVO: this.roles) {
				GrantedAuthority authority = new SimpleGrantedAuthority(roleVO.getRoleName());
				ar.add(authority);
			}
		 } else if (this.deptName != null) {
			 GrantedAuthority authority = new SimpleGrantedAuthority(this.deptName);
			 ar.add(authority);
		 }
		
		return ar;
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
		return this.status == 1;
	}
	
	

	
	
}
