package com.nnk.springboot.controllers;


import com.nnk.springboot.domain.CustomUserDetails;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;

import javassist.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

import javax.validation.Valid;

@Controller
public class UserController {
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;

    @RequestMapping("/user/list")
    public String home(Model model,Principal user){
    	CustomUserDetails userdetails =(CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	if(userdetails.getRole().equals("ADMIN")) {
    		  model.addAttribute("users", userService.findAllUser());
    	      return "user/list";
    	}
    	return "403";
      
    }

    @GetMapping("/user/add")
    public String addUser(User bid) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            userService.createUser(user);
            model.addAttribute("users", userService.findAllUser());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws NotFoundException {
        User user = userService.findOneById(id);
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) throws NotFoundException{
        if (result.hasErrors()) {
            return "user/update";
        }
        userService.updateUser(id,user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list" ;
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) throws NotFoundException {
        userService.deleteUserById(id);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
}
