package com.nnk.springboot.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
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
public class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;
	
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
		Mockito.when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
		userService.createUser(user);
		
		Mockito.verify(userRepository,Mockito.times(1)).save(any());	
	}
	@Test(expected = RuntimeException.class)
	public void createUserTestError() {	
		Mockito.when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
		userService.createUser(user);
		

	}
	
	
	@Test
	public void updateUser() throws NotFoundException {
		User userUpdate = new User();
		userUpdate.setFullname("Update");
		userUpdate.setId(1);
		userUpdate.setPassword("password_test");
		userUpdate.setUsername("username_test");
		
		Mockito.when(userRepository.existsById(1)).thenReturn(true);
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(userUpdate));
		Mockito.when(userRepository.save(userUpdate)).thenReturn(userUpdate);
		User u = userService.updateUser(1, userUpdate);
		
		Mockito.verify(userRepository, Mockito.times(1)).save(userUpdate);
	
		assertEquals(u.getFullname(),"Update");	
	}
	@Test
	public void updateUserError()  {
		
		Mockito.when(userRepository.existsById(1)).thenReturn(false);

		User u;
		try {
			u = userService.updateUser(1, user);
		} catch (NotFoundException e) {
			assertEquals(e.getMessage(),"User id doesn't exist");	
		}
	}
	
	@Test
	public void findAllTest() {
		List<User> testFind = new ArrayList<User>();
		testFind.add(user);
		Mockito.when(userRepository.findAll()).thenReturn(testFind);
		
		List<User> users = userService.findAllUser();
		assertEquals(users.size(),1);	
	}
	
	@Test
	public void findOneByIdTest() throws NotFoundException {
		Mockito.when(userRepository.existsById(1)).thenReturn(true);
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
		User usertest = userService.findOneById(1);
		
		assertEquals("fullName_test",usertest.getFullname());
	}
	
	@Test
	public void findOneByIdTestError() {
		Mockito.when(userRepository.existsById(user.getId())).thenReturn(false);
		try {
			userService.findOneById(1);
		} catch (NotFoundException e) {
			assertEquals(e.getMessage(),"Invalid user Id:1" );	
		}	
	}
	
	@Test
	public void deleteUserById() throws NotFoundException {
		Mockito.when(userRepository.existsById(user.getId())).thenReturn(true);	
		userService.deleteUserById(1);
		  Mockito.verify(userRepository, Mockito.times(1)).deleteById(anyInt());
	}
	@Test
	public void deleteUserByIdError() {
		Mockito.when(userRepository.existsById(user.getId())).thenReturn(false);
		
		try {
			userService.deleteUserById(1);
		} catch (NotFoundException e) {
			assertEquals(e.getMessage(),"Invalid user Id:1" );	
		}
	}
	

}
