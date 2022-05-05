package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;

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
public class RuleNameController {
   
	@Autowired
	private RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model){
        List<RuleName> ruleNameList = ruleNameService.getAllRuleName();
        model.addAttribute("ruleNameList",ruleNameList);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
    	RuleName ruleName = new RuleName();
    	model.addAttribute("ruleName",ruleName);
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if(result.hasErrors()) {
        	model.addAttribute("ruleName",ruleName);
        	return "ruleName/add";
        }
        ruleNameService.createRuleName(ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
        	RuleName ruleName = ruleNameService.getRuleNameById(id);
        	model.addAttribute("ruleName",ruleName);
        	  return "ruleName/update";
        }catch(NotFoundException e) {
        	return "notFound";
        }
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result, Model model) {
        try {
        	if(result.hasErrors()) {
        		model.addAttribute("ruleName",ruleName);
        		return "ruleName/update";
        	}
        	ruleNameService.updateRuleName(id, ruleName);
        	 return "redirect:/ruleName/list";
        }catch (NotFoundException e) {
        	return "notFound";
        }
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.deleteRuleName(id);
        return "redirect:/ruleName/list";
    }
}
