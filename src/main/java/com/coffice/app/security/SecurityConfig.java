package com.coffice.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.cors(cors-> cors.disable())
			.csrf(AbstractHttpConfigurer ::disable)
			
			.authorizeHttpRequests(auth ->{
					auth
					.requestMatchers("/notice/add", "/notice/update", "/notice/delete").hasRole("ADMIN")
					.requestMatchers("/user/mypage","/user/update","/user/logout").authenticated()
					.anyRequest().permitAll()
					;
			})
			
			.formLogin(formlogin ->{
				formlogin
				.loginPage("/user/login")
				.loginProcessingUrl("/user/login")
				.defaultSuccessUrl("/", true)
				.failureUrl("/user/login?error=true")
				;
			})
			;
		return httpSecurity.build();
					
	}
	
	@Bean
	WebSecurityCustomizer customizer() {
		return (web)->{
			web.ignoring()
				.requestMatchers("/css/**")
				.requestMatchers("/images/**","/img/**")
				.requestMatchers("/js/**")
				.requestMatchers("/vendor/**")
				;
		};
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
