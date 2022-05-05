package com.nnk.springboot.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

import javassist.NotFoundException;

@Service
public class BidListService {
	
	@Autowired
	private BidListRepository bidListRepository;
	
	private Logger log = LoggerFactory.getLogger(BidListService.class);
	
	public List<BidList> getAllBistList(){
		 log.info("Get all bidList");
		 List<BidList> bidList = bidListRepository.findAll();
		 return bidList;
	}
	
	public BidList getBidListById(Integer id) throws NotFoundException {
		log.info("Get bidList ID : "+id);
		if(bidListRepository.existsById(id)) {
			return bidListRepository.getOne(id);
		}
		log.error("bidList Id : "+id+" not exist");
		throw new NotFoundException("bidList Id : "+id+" not exist");
	}
	
	public BidList createNewBidList(BidList bidList) {
		log.info("create a new bidList");
		BidList newBid = new BidList();
		newBid.setAccount(bidList.getAccount());
		newBid.setBidQuantity(bidList.getBidQuantity());
		newBid.setType(bidList.getType());
		return bidListRepository.save(newBid);
	}
	
	
	@Transactional
	public void deleteBidList(Integer id) {
		log.info("Delete BidList id : "+ id);
		bidListRepository.deleteById(id);
	}
	
	
	@Transactional
	public BidList updateBidList(Integer id, BidList bidlist) throws NotFoundException {
		log.info("Update bidList id : "+id);
		if(bidListRepository.existsById(id)) {
			BidList bidListUpdated = bidListRepository.getOne(id);
			bidListUpdated.setAccount(bidlist.getAccount());
			bidListUpdated.setType(bidlist.getType());
			bidListUpdated.setBidQuantity(bidlist.getBidQuantity());
			return bidListRepository.save(bidListUpdated);
			}
		log.error("bidList Id : "+id+" not exist");
		throw new NotFoundException("bidList Id : "+id+" not exist");
	}

}
