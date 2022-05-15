package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import javassist.NotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BidListServiceTests {

	
	@Mock
	private BidListRepository bidListRepository;

	@InjectMocks
	private BidListService bidListService;
	
	private BidList bidList = new BidList();
	private BidList bidList2 = new BidList();

	@Before
	public void init() {
		bidList.setId(1);
		bidList.setAccount("test");
		bidList.setBidQuantity(1);
		
		bidList2.setId(2);
		bidList2.setAccount("test2");
		bidList2.setBidQuantity(2);
	}
	
	@Test
	public void createBidListTest() {
		Mockito.verify(bidListRepository,Mockito.times(0)).save(any());

		bidListService.createNewBidList(bidList);
		
		Mockito.verify(bidListRepository,Mockito.times(1)).save(any());
		
	}
	
	@Test
	public void updateBidListTest() throws NotFoundException{	
		Mockito.when(bidListRepository.existsById(1)).thenReturn(true);
		
		bidList.setBidQuantity(1);

		Mockito.when(bidListRepository.getOne(1)).thenReturn(bidList);

		BidList bidListUpdate = new BidList();
		bidListUpdate.setId(1);
		bidListUpdate.setAccount("update");
		bidListUpdate.setBidQuantity(1);

		
		bidListService.updateBidList(1, bidListUpdate);
		BidList bidListTest = bidListService.getBidListById(1);
			
		assertEquals(bidListUpdate.getAccount(),bidListTest.getAccount());

	}
	
	@Test(expected = NotFoundException.class)
	public void updateBidListTestThrowsError() throws NotFoundException{	
		Mockito.when(bidListRepository.existsById(1)).thenReturn(true);
		
		bidList.setBidQuantity(1);

		Mockito.when(bidListRepository.getOne(1)).thenReturn(bidList);

		BidList bidListUpdate = new BidList();
		bidListUpdate.setId(1);
		bidListUpdate.setAccount("update");
		bidListUpdate.setBidQuantity(1);

		
		bidListService.updateBidList(5, bidListUpdate);
		
	}
	
	@Test
	
	public void getAllBidListTest() {		
		List<BidList> listBidList = new ArrayList<>();
		listBidList.add(bidList);
		listBidList.add(bidList2);
		
		Mockito.when(bidListRepository.findAll()).thenReturn(listBidList);
		
		bidListService.createNewBidList(bidList);
		bidListService.createNewBidList(bidList2);
		
		List<BidList> allBidList = bidListService.getAllBistList();	
		assertEquals(allBidList.size(),2);
	}
	
	
	@Test
	public void deleteBidListTest() {
		Mockito.verify(bidListRepository,Mockito.times(0)).save(any());

		bidListService.createNewBidList(bidList);
		
		Mockito.verify(bidListRepository,Mockito.times(1)).save(any());
		
		bidListService.deleteBidList(1);
		
		Mockito.verify(bidListRepository,Mockito.times(1)).deleteById(any());
	}
	
	@Test(expected = NotFoundException.class)
	public void getBidListByIdNotExistTest() throws NotFoundException {
		
		assertEquals(bidListService.getBidListById(1), 0);
		
	}
}
