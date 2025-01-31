package com.nnk.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CustomOAuth2User;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
 
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService  {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user =  super.loadUser(userRequest);
        String username = user.getAttributes().get("login").toString();
        
        User userFind = userRepository.findByUsername(username);
		if (userFind == null) {
			CreateNewUser(username);
		}
        
        return new CustomOAuth2User(user);
    }
    
    /**
     * Create user if Oauth user not exists in bdd
     * @param userName
     */
    private void CreateNewUser(String userName) {
		User newUser = new User();
		newUser.setFullname(userName);
		newUser.setUsername(userName);
		newUser.setPassword(userService.passwordEncoder().encode(userName));
		userRepository.save(newUser);
    }
  
 
}