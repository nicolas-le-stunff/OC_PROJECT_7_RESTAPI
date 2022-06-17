package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;

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
public class TradeController {

	@Autowired
	private TradeService tradeService;

	@RequestMapping("/trade/list")
	public String home(Model model) {
		List<Trade> tradeList = tradeService.getAllTrade();
		model.addAttribute("tradeList", tradeList);
		return "trade/list";
	}

	@GetMapping("/trade/add")
	public String addUser(Model model) {
		Trade trade = new Trade();
		model.addAttribute("trade", trade);
		return "trade/add";
	}

	@PostMapping("/trade/validate")
	public String validate(@Valid Trade trade, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("trade", trade);
			return "trade/add";
		}
		tradeService.createTrade(trade);
		return "redirect:/trade/list";
	}

	@GetMapping("/trade/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		try {
			Trade tradeUpdate = tradeService.getTradeById(id);
			model.addAttribute("trade", tradeUpdate);
			return "trade/update";
		} catch (NotFoundException e) {
			return "errorNotFound";
		}
	}

	@PostMapping("/trade/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
		try {
			if (result.hasErrors()) {
				model.addAttribute("trade", trade);
				return "trade/update";
			}
			tradeService.updateTrade(id, trade);
			return "redirect:/trade/list";
		} catch (NotFoundException e) {
			return "errorNotFound";
		}
	}

	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {
		tradeService.deleteTrade(id);
		return "redirect:/trade/list";
	}
}
