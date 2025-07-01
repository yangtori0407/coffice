package com.coffice.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.coffice.app.users.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
    private LoginSuccessHandler loginSuccessHandler;
	
	@Autowired
	private UserService userService;


    SecurityConfig(LoginSuccessHandler loginSuccessHandler) {
        this.loginSuccessHandler = loginSuccessHandler;
    }
	
	
	@Bean
	public AuthenticationManager authenticationManager (HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception{
		return http
				.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userService)
				.passwordEncoder(passwordEncoder)
				.and()
				.build();
	}
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.cors(cors-> cors.disable())
			.csrf(AbstractHttpConfigurer ::disable)
			
			.authorizeHttpRequests(auth ->{
					auth
					.requestMatchers("/").authenticated()
					.requestMatchers("/notice/add", "/notice/update", "/notice/delete").hasRole("ADMIN")
					.requestMatchers("/employee/list","employee/detail", "/user/register").hasRole("ADMIN")
					.requestMatchers("/ingredients/list","/ingredients/detail","branch/add", "branch/masterAdd").hasAuthority("물류팀")
					.requestMatchers("/user/mypage","/user/update","/user/logout").authenticated()
					.requestMatchers("/document/**").authenticated()
					.anyRequest().permitAll()
					;
			})
			
			 .exceptionHandling(exception -> exception
			            .accessDeniedHandler((request, response, accessDeniedException) -> {
			            	 response.sendRedirect("/error/403");
			            })
			        )
			
			.formLogin(formlogin ->{
				formlogin
				.loginPage("/user/login")
				.loginProcessingUrl("/user/login")
				.usernameParameter("userId")
				.passwordParameter("password")
				.successHandler(loginSuccessHandler)
				.failureUrl("/user/login?error=true")
				.permitAll()
				;
			})
			
			.logout(logout->{
				logout
				.logoutUrl("/user/logout")
				.logoutSuccessUrl("/user/afterLogout")
				.invalidateHttpSession(true)
				//.permitAll()
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
