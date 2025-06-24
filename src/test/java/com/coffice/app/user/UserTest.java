package com.coffice.app.user;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.coffice.app.users.UserDAO;
import com.coffice.app.users.UserVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class UserTest {
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;

//	@Test
	void test() throws Exception {
		UserVO userVO = new UserVO();
		userVO.setUserId("HR941010");
		String pw = "19941010";
		
		boolean result = passwordEncoder.matches(pw, userVO.getPassword());
		assertTrue(result);
	}
	
	@Test
	void test2() throws Exception {
		String filename = UUID.randomUUID().toString();
		filename = filename.concat("-");
		log.info("fi:{}",filename);
	}

}
