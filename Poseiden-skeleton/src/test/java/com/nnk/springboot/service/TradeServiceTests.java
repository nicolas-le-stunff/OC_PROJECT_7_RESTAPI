package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import javassist.NotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeServiceTests {

	@Mock
	private TradeRepository tradeRepository;

	@InjectMocks
	private TradeService tradeService;
	
	private Trade trade = new Trade();
	private Trade trade2 = new Trade();
	
	@Before
	public void init() {
		trade.setBenchmark("BenchmarkTest");
		trade.setAccount("AccountTest");
		trade.setBuyQuantity(1.0);
		
		trade2.setBenchmark("BenchmarkTest1");
		trade2.setAccount("AccountTest1");
		trade2.setBuyQuantity(1.0);

	}

	@Test
	public void createTradeTest() {
		Mockito.verify(tradeRepository, Mockito.times(0)).save(any());

		tradeService.createTrade(trade);

		Mockito.verify(tradeRepository, Mockito.times(1)).save(any());
	}

	@Test
	public void updateTradeTest()throws NotFoundException {
		Mockito.when(tradeRepository.existsById(1)).thenReturn(true);


		Mockito.when(tradeRepository.getOne(1)).thenReturn(trade);

		Trade tradeUpdate = new Trade();
		tradeUpdate.setBenchmark("BenchmarkUpdate");
		tradeUpdate.setAccount("AccountUpdate");
		tradeUpdate.setBuyQuantity(1.0);


		tradeService.updateTrade(1, tradeUpdate);
		Trade tradeTest = tradeService.getTradeById(1);

		assertEquals(tradeUpdate.getAccount(), tradeTest.getAccount());
	}

	@Test(expected = NotFoundException.class)
	public void updateTradeTestThrowError()throws NotFoundException {
		Mockito.when(tradeRepository.existsById(1)).thenReturn(true);


		Mockito.when(tradeRepository.getOne(1)).thenReturn(trade);

		Trade tradeUpdate = new Trade();
		tradeUpdate.setBenchmark("BenchmarkUpdate");
		tradeUpdate.setAccount("AccountUpdate");
		tradeUpdate.setBuyQuantity(1.0);

		tradeService.updateTrade(5, tradeUpdate);
	}

	
	@Test
	public void getAllTradeTest() {


		List<Trade> tradeList = new ArrayList<>();
		tradeList.add(trade);
		tradeList.add(trade2);

		Mockito.when(tradeRepository.findAll()).thenReturn(tradeList);

		tradeService.createTrade(trade);
		tradeService.createTrade(trade2);

		List<Trade> allTradeList = tradeService.getAllTrade();
		assertEquals(allTradeList.size(), 2);
	}

	@Test
	public void deleteTradeTest() {
		Mockito.verify(tradeRepository, Mockito.times(0)).save(any());

		tradeService.createTrade(trade);

		Mockito.verify(tradeRepository, Mockito.times(1)).save(any());
		
		tradeService.deleteTrade(1);
		
		Mockito.verify(tradeRepository,Mockito.times(1)).deleteById(any());
		
	}
	
	@Test
	public void getTradeByIdTest() throws NotFoundException {
		Mockito.when(tradeRepository.existsById(1)).thenReturn(true);


		Mockito.when(tradeRepository.getOne(1)).thenReturn(trade);
		
		tradeService.createTrade(trade);
		Trade tra = tradeService.getTradeById(1);

		assertEquals(tra, trade);
		
	}
	
	@Test(expected = NotFoundException.class)
	public void getTradeByIdNotExistTest() throws NotFoundException {
		
		assertEquals(tradeService.getTradeById(1), 0);
		
	}
}
