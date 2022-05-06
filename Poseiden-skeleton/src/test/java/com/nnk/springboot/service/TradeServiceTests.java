package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.BidListService;
import com.nnk.springboot.service.TradeService;

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
public class TradeServiceTests {

	@Mock
	private TradeRepository tradeRepository;

	@InjectMocks
	private TradeService tradeService;

	@Test
	public void createTradeTest() {
		Mockito.verify(tradeRepository, Mockito.times(0)).save(any());

		Trade trade = new Trade();
		trade.setBenchmark("BenchmarkTest");
		trade.setAccount("AccountTest");
		trade.setBuyQuantity(1.0);

		tradeService.createTrade(trade);

		Mockito.verify(tradeRepository, Mockito.times(1)).save(any());

	}

	@Test
	public void updateTradeTest() {
		Mockito.when(tradeRepository.existsById(1)).thenReturn(true);

		Trade trade = new Trade();
		trade.setBenchmark("BenchmarkTest");
		trade.setAccount("AccountTest");
		trade.setBuyQuantity(1.0);

		Mockito.when(tradeRepository.getOne(1)).thenReturn(trade);

		Trade tradeUpdate = new Trade();
		tradeUpdate.setBenchmark("BenchmarkUpdate");
		tradeUpdate.setAccount("AccountUpdate");
		tradeUpdate.setBuyQuantity(1.0);

		try {
			tradeService.updateTrade(1, tradeUpdate);
			Trade tradeTest = tradeService.getTradeById(1);

			assertEquals(tradeUpdate.getAccount(), tradeTest.getAccount());

		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void getAllTradeTest() {

		Trade trade = new Trade();
		trade.setBenchmark("BenchmarkTest");
		trade.setAccount("AccountTest");
		trade.setBuyQuantity(1.0);

		Trade trade1 = new Trade();
		trade1.setBenchmark("BenchmarkTest1");
		trade1.setAccount("AccountTest1");
		trade1.setBuyQuantity(1.0);

		List<Trade> tradeList = new ArrayList<>();
		tradeList.add(trade);
		tradeList.add(trade1);

		Mockito.when(tradeRepository.findAll()).thenReturn(tradeList);

		tradeService.createTrade(trade);
		tradeService.createTrade(trade1);

		List<Trade> allTradeList = tradeService.getAllTrade();
		assertEquals(allTradeList.size(), 2);
	}
	/*
	 * @Autowired private TradeRepository tradeRepository;
	 * 
	 * @Test public void tradeTest() { Trade trade = new Trade("Trade Account",
	 * "Type");
	 * 
	 * 
	 * // Find List<Trade> listResult = tradeRepository.findAll();
	 * Assert.assertTrue(listResult.size() > 0);
	 * 
	 * // Delete Integer id = trade.getTradeId(); tradeRepository.delete(trade);
	 * Optional<Trade> tradeList = tradeRepository.findById(id);
	 * Assert.assertFalse(tradeList.isPresent()); }
	 */
}
