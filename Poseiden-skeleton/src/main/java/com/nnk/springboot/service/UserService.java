package com.nnk.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	private static Logger log = LoggerFactory.getLogger(UserService.class);

	public User createUser(User user) {
		if(!userRepository.existsByUsername(user.getUsername())) {
			user.setPassword(passwordEncoder().encode(user.getPassword()));
			user.setFullname(user.getFullname());
			user.setUsername(user.getUsername());
			user.setRole(user.getRole());
			log.info("new user :" + user.getFullname() +" "+ user.getUsername());
			return userRepository.save(user);
		}
		log.error("User already exists : username "+user.getUsername());
		throw new RuntimeException("User already exists : username "+user.getUsername());
		
	}
	
	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();	
	}
}
