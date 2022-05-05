package com.nnk.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;


import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	public void createUser(User user) {
		user.setPassword(passwordEncoder().encode(user.getPassword()));
		user.setFullname(user.getFullname());
		user.setUsername(user.getUsername());
		user.setRole(user.getRole());
		logger.info("new user :" + user.getFullname() +" "+ user.getUsername());
		userRepository.save(user);
	}
	
	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();	
	}
}
