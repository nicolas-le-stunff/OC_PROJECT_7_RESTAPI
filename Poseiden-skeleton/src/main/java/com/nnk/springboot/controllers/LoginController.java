package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;

import java.security.Principal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;
    
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	private final OAuth2AuthorizedClientService authorizedClientService;
	
	public LoginController(OAuth2AuthorizedClientService authorizedClientService) {
		this.authorizedClientService = authorizedClientService;
	}

	   @GetMapping("/login")
	    public String login() {
	    	logger.info(" login page");
	        ModelAndView mav = new ModelAndView();
	        mav.setViewName("login");
	        return "login";
	    }
	   
		@RequestMapping("/")
		public String home(Model model)
		{
			return "home";
		}
}
