package com.nnk.springboot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import static org.mockito.ArgumentMatchers.any;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;
	
	@Test
	public void createUserTest() {	
		User user = new User();
		user.setFullname("fullName_test");
		user.setId(1);
		user.setPassword("password_test");
		user.setUsername("username_test");
		Mockito.when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
		userService.createUser(user);
		
		Mockito.verify(userRepository,Mockito.times(1)).save(any());
		
	}

}
