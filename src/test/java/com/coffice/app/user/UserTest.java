package com.coffice.app.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.coffice.app.users.UserDAO;
import com.coffice.app.users.UserVO;

@SpringBootTest
class UserTest {
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void test() throws Exception {
		UserVO userVO = new UserVO();
		userVO.setUserId("HR941010");
		String pw = "19941010";
		
		boolean result = passwordEncoder.matches(pw, userVO.getPassword());
		assertTrue(result);
	}

}
