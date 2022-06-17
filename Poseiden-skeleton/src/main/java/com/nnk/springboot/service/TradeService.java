package com.nnk.springboot.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

import javassist.NotFoundException;

@Service
public class TradeService {

	@Autowired
	private TradeRepository tradeRepository;

	private Logger log = LoggerFactory.getLogger(TradeService.class);

	/**
	 * Get all Ttrade
	 * 
	 * @return List of Trade
	 */
	public List<Trade> getAllTrade() {
		log.info("Get all trade");
		return tradeRepository.findAll();
	}

	/**
	 * Get trade by Id
	 * 
	 * @param id
	 * @return trade
	 * @throws NotFoundException
	 */
	public Trade getTradeById(Integer id) throws NotFoundException {
		log.info("Get trade by ID : " + id);
		if (tradeRepository.existsById(id)) {
			return tradeRepository.getOne(id);
		}
		log.error("trade Id : " + id + " not exist");
		throw new NotFoundException("ruleName Id : " + id + " not exist");

	}

	/**
	 * Update trade by Id
	 * 
	 * @param id
	 * @param trade
	 * @return trade
	 * @throws NotFoundException
	 */
	@Transactional
	public Trade updateTrade(Integer id, Trade trade) throws NotFoundException {
		log.info("Update trade ID : " + id);
		if (tradeRepository.existsById(id)) {
			Trade tradeUpdate = tradeRepository.getOne(id);
			tradeUpdate.setAccount(trade.getAccount());
			tradeUpdate.setType(trade.getType());
			tradeUpdate.setBuyQuantity(trade.getBuyQuantity());
			return tradeRepository.save(tradeUpdate);
		}
		log.error("trade Id : " + id + " not exist");
		throw new NotFoundException("ruleName Id : " + id + " not exist");
	}

	/**
	 * Delete trade by ID
	 * 
	 * @param id
	 */
	@Transactional
	public void deleteTrade(Integer id) {
		log.info("Delete trade : " + id);
		tradeRepository.deleteById(id);
	}

	/**
	 * Create trade
	 * 
	 * @param trade
	 * @return trade
	 */
	public Trade createTrade(Trade trade) {
		log.info("Create new trade");
		Trade newtrade = new Trade();
		newtrade.setAccount(trade.getAccount());
		newtrade.setType(trade.getType());
		newtrade.setBuyQuantity(trade.getBuyQuantity());
		return tradeRepository.save(newtrade);
	}

}
