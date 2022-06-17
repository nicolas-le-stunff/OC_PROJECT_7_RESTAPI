package com.nnk.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

import javassist.NotFoundException;

import java.util.List;
import java.util.Optional;

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
	
	public void deleteUserById(int id) throws NotFoundException {
		if(userRepository.existsById(id)){
		
			userRepository.deleteById(id);
		
		}else {
			throw new NotFoundException("Invalid user Id:" + id);
		}
	}
	
	public User findOneById(int id) throws NotFoundException {
		
		if (userRepository.existsById(id)) {
			return userRepository.findById(id).get();
		}else{
			 throw new NotFoundException("Invalid user Id:" + id);
		}
	}
	
	public User updateUser(int id,User user) throws NotFoundException {
		
		if (userRepository.existsById(id)) {
			User userRepo = userRepository.findById(id).get();
			userRepo.setFullname(user.getFullname());
			userRepo.setRole(user.getRole());
			userRepo.setPassword(passwordEncoder().encode(user.getPassword()));
			userRepo.setUsername(user.getUsername());
			return userRepository.save(userRepo);
		}
        log.error("Unable to get user by id : " + id + " because doesn't exist");
        throw new NotFoundException("User id doesn't exist");
	}
	
	public List<User> findAllUser() {
		return userRepository.findAll();
	}
	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();	
	}
}
