package com.nnk.springboot.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;

import javassist.NotFoundException;

@Controller
public class RatingController {
    
    @Autowired
	private RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model,Principal user)
    {
        List<Rating> ratingList = ratingService.getAllRating();
        model.addAttribute("ratingList",ratingList);
        model.addAttribute("user",user.toString());
        
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
    	Rating rating = new Rating();
    	model.addAttribute("rating",rating);
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
    	if(result.hasErrors()) {
    		model.addAttribute("rating",rating);
    		return "rating/add";
    	}
        ratingService.createRating(rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
			Rating rating = ratingService.getRatingById(id);
			model.addAttribute("rating",rating);
			return "rating/update";
		} catch (NotFoundException e) {
			return "errorNotFound";
		}
     
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        try {
        	if(result.hasErrors()) {
        		model.addAttribute("rating", rating);
        		return "redirect:/rating/update";
        	}
        	ratingService.updateRating(id, rating);
        	return "redirect:/rating/list";
        }catch(NotFoundException e) {
        	return "errorNotFound";
        }
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.deleteRating(id);
        return "redirect:/rating/list";
    }
}
