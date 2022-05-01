package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;

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
public class BidListController {
    
	@Autowired
	private BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        List<BidList> bidList = bidListService.getAllBistList();
        model.addAttribute("bidList",bidList);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        BidList bidList = new BidList();
        model.addAttribute("bidListDto", bidList);
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
    	if(result.hasErrors()) {
    		model.addAttribute("bidList",bid);
    		return "bidList/add";
    	}
    	bidListService.createNewBidList(bid);
        return "bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
        	BidList bidList = bidListService.getBidListById(id);
        	model.addAttribute("bidList", bidList);
        	return "bidList/update"; 	
        }catch(NotFoundException e) {
        	return "errorNotFound";
        }
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
        try {
            if (!result.hasErrors()) {
                bidListService.updateBidList(id, bidList);
                return "redirect:/bidList/list";
            }
            model.addAttribute("bidListDto", bidList);
            return "bidList/update";
        } catch (NotFoundException e) {
            return "errorNotFound";
        }
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.deleteBidList(id);
        return "redirect:/bidList/list";
    }
}
