package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.BidListService;
import com.nnk.springboot.service.RatingService;

import javassist.NotFoundException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BidListServiceTests {

	
	
	@Mock
	private BidListRepository bidListRepository;

	@InjectMocks
	private BidListService bidListService;
	
	@Test
	public void createBidListTest() {
		Mockito.verify(bidListRepository,Mockito.times(0)).save(any());
		
		BidList bidList = new BidList();
		bidList.setId(1);
		bidList.setAccount("test");
		bidList.setBidQuantity(1);

		
		bidListService.createNewBidList(bidList);
		
		Mockito.verify(bidListRepository,Mockito.times(1)).save(any());
		
	}
	
	@Test
	public void updateBidListTest() {	
		Mockito.when(bidListRepository.existsById(1)).thenReturn(true);
		
		
		BidList bidList = new BidList();
		bidList.setId(1);
		bidList.setAccount("test");
		bidList.setBidQuantity(1);

		
		Mockito.when(bidListRepository.getOne(1)).thenReturn(bidList);
		
		
		BidList bidListUpdate = new BidList();
		bidListUpdate.setId(1);
		bidListUpdate.setAccount("update");
		bidListUpdate.setBidQuantity(1);

		
		
		try {
			bidListService.updateBidList(1, bidListUpdate);
			BidList bidListTest = bidListService.getBidListById(1);
			
			assertEquals(bidListUpdate.getAccount(),bidListTest.getAccount());
			
			
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
		
	}
	
	@Test
	
	public void getAllBidListTest() {
		
	
		BidList bidList = new BidList();
		bidList.setId(1);
		bidList.setAccount("test");
		bidList.setBidQuantity(1);
		
		BidList bidList2 = new BidList();
		bidList.setId(2);
		bidList.setAccount("test2");
		bidList.setBidQuantity(2);
		
		List<BidList> listBidList = new ArrayList<>();
		listBidList.add(bidList);
		listBidList.add(bidList2);
		
		Mockito.when(bidListRepository.findAll()).thenReturn(listBidList);
		
		bidListService.createNewBidList(bidList);
		bidListService.createNewBidList(bidList2);
		
		List<BidList> allBidList = bidListService.getAllBistList();	
		assertEquals(allBidList.size(),2);
	}
	
/*	@Autowired
	private BidListRepository bidListRepository;

	@Test
	public void bidListTest() {
		BidList bid = new BidList("Account Test", "Type Test", 10d);

		// Save
		bid = bidListRepository.save(bid);
		Assert.assertNotNull(bid.getBidListId());
		Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);

		// Update
		bid.setBidQuantity(20d);
		bid = bidListRepository.save(bid);
		Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);

		// Find
		List<BidList> listResult = bidListRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = bid.getBidListId();
		bidListRepository.delete(bid);
		Optional<BidList> bidList = bidListRepository.findById(id);
		Assert.assertFalse(bidList.isPresent());
	}*/
}
