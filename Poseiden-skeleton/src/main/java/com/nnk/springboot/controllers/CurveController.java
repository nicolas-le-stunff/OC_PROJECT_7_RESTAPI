package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;

import javassist.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import javax.validation.Valid;

@Controller
public class CurveController {
    
	
	@Autowired
	private CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model){        
    	List<CurvePoint> curvePoint = curvePointService.getAllCurvePoint();
    	model.addAttribute("curvePointList", curvePoint);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(Model model) {
        CurvePoint curvePoint = new CurvePoint();
        model.addAttribute("curvePoint", curvePoint); 
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("curvePoint", curvePoint);         
            return "curvePoint/add";
        }
        curvePointService.createCurvePoint(curvePoint);
        return "redirect:/curvePoint/list";
    
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            CurvePoint curvePointUpdates = curvePointService.getCurvePointById(id);
            model.addAttribute("curvePoint", curvePointUpdates);
            return "curvePoint/update";
        } catch (NotFoundException e) {
            return "notFound";
        }
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result, Model model) {
        try {
        	if(result.hasErrors()) {
        		model.addAttribute("curvePoint",curvePoint);
        		return "curvePoint/update";
        	}
        	curvePointService.updateCurvePoint(id,curvePoint);
        	return "redirect:/curvePoint/list";
        }catch (NotFoundException e) {
        	return "errorNotFound";
        }
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        curvePointService.deleteCurvePoint(id);
        return "redirect:/curvePoint/list";
    }
}
