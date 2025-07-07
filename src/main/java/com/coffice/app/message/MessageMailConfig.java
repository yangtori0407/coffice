package com.coffice.app.message;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MessageMailConfig {
	private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	private static final String MAIL_DEBUG = "mail.smtp.debug";
	private static final String MAIL_CONNECTION_TIMEOUT = "mail.smtp.connectiontimeout";
	private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	
	@Value("${spring.gmail.host}")
	private String host;
	
	@Value("${spring.gmail.username}")
	private String username;
	
	@Value("${spring.gmail.password}")
	private String password;
	
	@Value("${spring.gmail.port}")
	private int port;
	
	@Value("${spring.gmail.properties.mail.smtp.auth}")
	private boolean auth;
	
	@Value("${spring.gmail.smtp.debug}")
	private boolean debug;
	
	@Value("${spring.smtp.connectiontimeout}")
	private int connectionTimeout;
	
	@Value("${spring.gmail.properties.mail.smtp.starttls.enable}")
	private boolean startTlsEnable;
	
	@Bean
	public JavaMailSender messageMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(host);
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);
		javaMailSender.setPort(port);
		
		Properties properties = javaMailSender.getJavaMailProperties();
		properties.put(MAIL_SMTP_AUTH, auth);
		properties.put(MAIL_DEBUG, debug);
		properties.put(MAIL_CONNECTION_TIMEOUT, connectionTimeout);
		properties.put(MAIL_SMTP_STARTTLS_ENABLE, startTlsEnable);
		
		javaMailSender.setJavaMailProperties(properties);
		javaMailSender.setDefaultEncoding("UTF-8");
		
		return javaMailSender;
	}
}
