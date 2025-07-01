package com.coffice.app.users;


import java.util.Random;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.files.FileManager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private EmployeeRoleDAO employeeRoleDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private FileManager fileManager;
	
	@Value("${app.profiles.base}")
	private String path;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
	    try {
	        UserVO userVO = new UserVO();
	        userVO.setUserId(userId);

	        userVO = userDAO.detail(userVO);

	        if (userVO == null) {
	            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId);
	        }

	        // 권한 정상 출력되는지 확인
	        System.out.println("▶ userVO.getAuthorities(): " + userVO.getAuthorities());

	        return userVO;

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new UsernameNotFoundException("DB 조회 중 오류 발생: " + e.getMessage());
	    }
	}

	
	//등록 시 입력값 유효성 검사를 위한 검증 메서드
	public boolean userErrorCheck(UserVO userVO, BindingResult bindingResult) throws Exception{
		boolean check=false;
		
		check = bindingResult.hasErrors();
		
		
		if(!userVO.getPassword().equals(userVO.getPasswordCheck())) {
			check =true;
			bindingResult.rejectValue("passwordCheck", null, "비밀번호가 일치하지 않습니다");
		}
		
		if (bindingResult.hasErrors()) {
		    bindingResult.getFieldErrors().forEach(err -> {
		        //System.out.println("유효성 오류 - field: " + err.getField() + ", msg: " + err.getDefaultMessage());
		    });
		}
		
		UserVO checkVO = userDAO.detail(userVO);
		if(checkVO != null) {
			check=true;
			bindingResult.rejectValue("userId", "userVO.userId.equal");
		}
		
		return check;
	}
	
	public int register(UserVO userVO, MultipartFile file) throws Exception {
		
		if(userVO.getUserId()==null || userVO.getUserId().isBlank()) {
			String employeeId;
			do {
				employeeId = String.format("co%06d", new Random().nextInt(1_000_000));
			} while (userDAO.existUserId(employeeId));
			userVO.setUserId(employeeId);
		}
		
		if(!file.isEmpty()) {
			String fileName = fileManager.fileSave(path , file);
			userVO.setSaveName(fileName);
			userVO.setOriginName(file.getOriginalFilename());
		}
		userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
		
		
		int result = userDAO.register(userVO);
		
		Long userRoleId = roleDAO.findRoleIdByName("ROLE_EMPLOYEE");
		
	    EmployeeRoleVO userRole = new EmployeeRoleVO();
	    userRole.setUserId(userVO.getUserId());
	    userRole.setRoleId(userRoleId);
	    employeeRoleDAO.insertRole(userRole);
	    
	    return result;
	}
	
	public UserVO detail(UserVO userVO) throws Exception{
		UserVO result = userDAO.detail(userVO);
		//log.info("입력한 아이디: {}", userVO.getUserId());
		if(result != null) {
			if(userVO.getPassword().equals(result.getPassword())) {
				return result;
			}
			result = null;
		}
		return result;
	}
	
	public UserVO findByEmail(String email) throws Exception {
		return userDAO.findByEmail(email);
	}
	
	public UserVO findById(String userId) throws Exception {
	    return userDAO.findById(userId);
	}
	
	public int updatePassword(String userId, String newPassword) throws Exception {
		String encodedPassword = passwordEncoder.encode(newPassword);
		
		UserVO userVO = new UserVO();
		userVO.setUserId(userId);
		userVO.setPassword(encodedPassword);
		
		return userDAO.updatePassword(userVO);
	}
	
	public boolean checkPassword(String userId, String inputPassword) throws Exception {
		UserVO userVO = userDAO.findById(userId);
		if (userVO == null) {
			//System.out.println("❌ 사용자 정보 없음: " + userId);
			return false;
		}
		
		 //System.out.println("🔑 입력 비밀번호 (평문): " + inputPassword);
		 //System.out.println("🧾 DB 비밀번호 (암호화): " + userVO.getPassword());
		
		 boolean matches = passwordEncoder.matches(inputPassword, userVO.getPassword());
		 //System.out.println("✅ 비밀번호 일치 여부: " + matches);
		 
		 return matches;
	}
	
	public int update(UserVO userVO, MultipartFile file) throws Exception {
		UserVO originalUser = userDAO.findById(userVO.getUserId());
		
		if(!file.isEmpty()) {
			String fileName = fileManager.fileSave(path, file);
			userVO.setSaveName(fileName);
			userVO.setOriginName(file.getOriginalFilename());
		} else {
			userVO.setSaveName(originalUser.getSaveName());
			userVO.setOriginName(originalUser.getOriginName());
		}
		
		if(userVO.getPassword() != null && !userVO.getPassword().isBlank()) {
			if (!userVO.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
	            throw new IllegalArgumentException("비밀번호는 8자리 이상이며, 영문자와 숫자를 포함해야 합니다.");
	        }
			userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
		}else {
			userVO.setPassword(originalUser.getPassword()); //변경 안했음 유지
		}
		
		return userDAO.update(userVO);
	}
	
	
	
	
	
	// 조직도
	public List<DepartmentVO> getDeps() throws Exception {
		return userDAO.getDeps();
	}
	
	public List<UserVO> getUsers(UserVO userVO) throws Exception {
		return userDAO.getUsers(userVO);
	}

}
