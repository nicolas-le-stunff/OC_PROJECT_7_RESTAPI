package com.nnk.springboot.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.TradeService;

import javassist.NotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(TradeController.class)
public class TradeControllerTest {
	
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private TradeService tradeService;
    
    @MockBean
    private TradeRepository tradeRepository;
	
    private Trade trade = new Trade();
    
    

    @BeforeEach
    private void addTrade() {
		trade.setAccount("account");
		trade.setType("type");
		trade.setBuyQuantity(1.0);
		tradeService.createTrade(trade);
    }

    @Test
    @WithMockUser(username="user",roles="ADMIN")
    public void getTradeTest() throws Exception {

        mockMvc.perform(get("/trade/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"));
    }
    
    @Test
    @WithMockUser(username="user",roles="ADMIN")
    public void getTradeAddTest() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("trade"));
         
    }
    
    @Test
    @WithMockUser(username="user",roles="ADMIN")
    public void postTradeTest() throws Exception {
		trade.setAccount("account");
		trade.setType("type");
		trade.setBuyQuantity(1.0);
		
        mockMvc.perform(post("/trade/validate")
                .param("account", trade.getAccount())
                .param("type", trade.getType())
                .param("buyQuantity", String.valueOf(trade.getBuyQuantity()))
                .with(csrf()))
                .andDo(print())
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(status().is3xxRedirection());
      
    }

    @Test
    @WithMockUser(username="user",roles="ADMIN")
    public void postTradeWithErrorTest() throws Exception {
        mockMvc.perform(post("/trade/validate")
                .param("account", trade.getAccount())
                .param("type", trade.getType())
                .param("buyQuantity", String.valueOf(trade.getBuyQuantity()))
                .with(csrf()))
                .andDo(print())
                .andExpect(view().name("trade/add"))
                .andExpect(status().is2xxSuccessful());
            
    }

    @Test
    @WithMockUser(username="user",roles="ADMIN")
    public void getTradetUpdateByExistingIdTest() throws Exception {
		trade.setAccount("account");
		trade.setType("type");
		trade.setBuyQuantity(1.0);
		
		when(tradeService.getTradeById(1)).thenReturn(trade);

        mockMvc.perform(get("/trade/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"));
    }

    @Test
    @WithMockUser(username="user",roles="ADMIN")
    public void getTradeUpdateByUnknownIdTest() throws Exception {
		trade.setAccount("account");
		trade.setType("type");
		trade.setBuyQuantity(1.0);
		
		

		when(tradeService.getTradeById(5)).thenThrow(new NotFoundException("trade Id : "+5+" not exist"));
		
        mockMvc.perform(get("/trade/update/5")
                .param("account", trade.getAccount())
                .param("type", trade.getType())
                .param("buyQuantity", String.valueOf(trade.getBuyQuantity()))
        		.with(csrf()))   
                .andExpect(status().isOk())
                .andExpect(view().name("errorNotFound"));
    }

    
    @Test
    @WithMockUser(username="user",roles="ADMIN")
    public void postTradeUpdateTest() throws Exception {
		trade.setAccount("account");
		trade.setType("type");
		trade.setBuyQuantity(1.0);
		
    	when(tradeService.updateTrade(1, trade)).thenReturn(trade);
    		
        mockMvc.perform(post("/trade/update/1")
                .param("account", trade.getAccount())
                .param("type", trade.getType())
                .param("buyQuantity", String.valueOf(trade.getBuyQuantity()))
                .with(csrf()))
              	.andDo(print())
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(status().is3xxRedirection());
    }

 

	@Test
    @WithMockUser(username="user",roles="ADMIN")
    public void postTradeUpdateWithErrorTest() throws Exception {
		
        mockMvc.perform(post("/trade/update/1")
                .param("account", trade.getAccount())
                .param("type", trade.getType())
                .param("buyQuantity", String.valueOf(trade.getBuyQuantity()))
                .with(csrf()))
                .andDo(print())
                .andExpect(view().name("trade/update"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("trade"))
                .andReturn().getResponse().containsHeader("Account is mandatory");
    }



    @Test
    @WithMockUser(username="user",roles="ADMIN")
    public void getTradeDeleteByIdTest() throws Exception {

        mockMvc.perform(get("/trade/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"));
    }
}
    
    