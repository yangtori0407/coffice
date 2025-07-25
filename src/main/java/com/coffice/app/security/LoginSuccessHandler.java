package com.coffice.app.security;

import java.io.IOException;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.coffice.app.users.UserVO;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		//System.out.println("✅ 로그인 성공!");
		
		UserVO userVO = (UserVO) authentication.getPrincipal();
		
		HttpSession session = request.getSession();
		session.setAttribute("user", userVO);
		Cookie cookie = new Cookie("userId", userVO.getUserId());
		cookie.setPath("/");
		response.addCookie(cookie);
		response.sendRedirect("/");
		
		
	}

	
}
