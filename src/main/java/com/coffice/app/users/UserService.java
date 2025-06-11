package com.coffice.app.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.coffice.app.files.FileManager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private FileManager fileManager;
	@Value("${app.profiles.base}")
	private String path;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		UserVO userVO = new UserVO();
		userVO.setUserId(userId);
		//System.out.println("로그인 요청 아이디 : "+ userId);
		try {
			userVO = userDAO.detail(userVO);
			if (userVO == null) {
	            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId);
	        }
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new UsernameNotFoundException("DB 조회 중 오류 발생: " + e.getMessage());
		}
		return userVO;
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
		if(!file.isEmpty()) {
			String fileName = fileManager.fileSave(path , file);
			userVO.setSaveName(fileName);
			userVO.setOriginName(file.getOriginalFilename());
		}
		userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
		return userDAO.register(userVO);
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
	
	

}
