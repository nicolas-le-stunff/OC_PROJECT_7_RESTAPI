package com.nnk.springboot.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.CustomUserDetailsService;
import com.nnk.springboot.service.UserService;
import static org.mockito.Mockito.when;

import javassist.NotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
	
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserRepository userRepository;
    
    @Mock
    private CustomUserDetailsService customUserDetailsService;
    
    @MockBean
    private UserService userService;

   /* @Test
   
   public void UserList() throws Exception {
    	Mockito.when(userRepository.existsById(1)).thenReturn(true);
        mockMvc.perform(get("/user/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/List"));
    }*/
    private User user = new User();
	@Before
	public void init() {

		user.setFullname("fullName_test");
		user.setId(1);
		user.setPassword("password_test");
		user.setUsername("username_test");
		
	}
    
    @Test
    public void userValidate() throws Exception {
    	Mockito.when(userService.createUser(any())).thenReturn(user);
    	
    	mockMvc.perform(post("/user/validate")
    			.param("fullname", "ok")
    			.param("username","okok")
    			.param("role", "role")
    			.param("id","1"))
    	.andDo(print())
    	.andExpect(view().name("redirect:/user/list"))
    	.andExpect(status().is3xxRedirection());
    }
    
    @Test
    public void userGetUpdateTest() throws Exception {
    	Mockito.when(userService.findOneById(1)).thenReturn(user);
    	
    	mockMvc.perform(get("/user/update/1"))
    	.andDo(print())
    	.andExpect(view().name("user/update"))
    	.andExpect(status().is2xxSuccessful());
    }
    @Test
    public void userUpdateTest() throws Exception {
    	mockMvc.perform(post("/user/update/1"))
    	.andDo(print())
    	.andExpect(view().name("redirect:/user/list"))
    	.andExpect(status().is3xxRedirection());
    }
    
    
    @Test
    public void userAddTest() throws Exception {
        mockMvc
        .perform(get("/user/add"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/add"));
    }
    @Test
    public void userDeleteTest() throws Exception {
    	mockMvc
    	.perform(get("/user/delete/1"))
    	.andDo(print())
    	.andExpect(status().is3xxRedirection())
    	.andExpect(view().name("redirect:/user/list"));
    }

    
}
