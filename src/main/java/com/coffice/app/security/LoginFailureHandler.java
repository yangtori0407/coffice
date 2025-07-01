package com.coffice.app.security;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		String errorType = "error";

        if (exception instanceof DisabledException) {
            errorType = "disabled";
        } else if (exception instanceof BadCredentialsException) {
            errorType = "badCredentials";
        }

        getRedirectStrategy().sendRedirect(request, response, "/user/login?error=" + errorType);
	}
	
	

}
