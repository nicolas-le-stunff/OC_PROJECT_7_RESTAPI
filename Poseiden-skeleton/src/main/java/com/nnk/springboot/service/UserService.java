package com.nnk.springboot.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);

}
