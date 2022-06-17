package com.nnk.springboot.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

import javassist.NotFoundException;
import net.bytebuddy.asm.Advice.Thrown;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomUserDetailsServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;
	
	@InjectMocks 
	private CustomUserDetailsService customUserDetailService;
	
	private User user = new User();
	
	@Before
	public void init() {
		user.setFullname("fullName_test");
		user.setId(1);
		user.setPassword("password_test");
		user.setUsername("username_test");
		
	}
	
	@Test
	public void createUserTest() {	
		Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
		UserDetails userTest = customUserDetailService.loadUserByUsername(user.getUsername());
		
		assertEquals(userTest.getUsername(),user.getUsername());	
	}
	@Test(expected = AssertionError.class )
	public void loadUserByUserNameError() {	
		Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
		try{
			customUserDetailService.loadUserByUsername(user.getUsername());
		}catch(Exception e) {
			assertEquals(e, "e");
		}
	}	

}
