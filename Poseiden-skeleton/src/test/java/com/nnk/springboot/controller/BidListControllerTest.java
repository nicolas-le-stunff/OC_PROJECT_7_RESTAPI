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

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;

import javassist.NotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(BidListController.class)
public class BidListControllerTest {
	
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private BidListService bidListService;
    
    @MockBean
    private BidListRepository bidListRepository;
	
    private BidList bidList = new BidList();
    
    

    @BeforeEach
    private void addBid() {
		bidList.setId(1);
		bidList.setAccount("test");
		bidList.setBidQuantity(10d);
		bidList.setType("type");
        bidListService.createNewBidList(bidList);
    }

    @Test
    @WithMockUser(username="user",roles="ADMIN")
    public void getBidListTest() throws Exception {

        mockMvc.perform(get("/bidList/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"));
    }
    
    @Test
    @WithMockUser(username="user",roles="ADMIN")
    public void getBidListAddTest() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeExists("bidList"));
         
    }
    
    @Test
    @WithMockUser(username="user",roles="ADMIN")
    public void postNewBidTest() throws Exception {
		bidList.setId(1);
		bidList.setAccount("test");
		bidList.setBidQuantity(10);
		bidList.setType("type");
		
        mockMvc.perform(post("/bidList/validate")
                .param("account", bidList.getAccount())
                .param("type", bidList.getType())
                .param("bidQuantity", String.valueOf(bidList.getBidQuantity()))
                .with(csrf()))
                .andDo(print())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(status().is3xxRedirection())
                .andReturn().getResponse().containsHeader("accountTest");
    }

    @Test
    @WithMockUser(username="user",roles="ADMIN")
    public void postNewBidWithErrorTest() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                .param("account", bidList.getAccount())
                .param("type", bidList.getType())
                .param("bidQuantity", String.valueOf(bidList.getBidQuantity()))
                .with(csrf()))
                .andDo(print())
                .andExpect(view().name("bidList/add"))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().containsHeader("accountTest");
    }

    @Test
    @WithMockUser(username="user",roles="ADMIN")
    public void getBidListUpdateByExistingIdTest() throws Exception {
		bidList.setId(1);
		bidList.setAccount("test");
		bidList.setBidQuantity(1);
		bidList.setType("type");
		
		when(bidListService.getBidListById(1)).thenReturn(bidList);

        mockMvc.perform(get("/bidList/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"));
    }

    @Test
    @WithMockUser(username="user",roles="ADMIN")
    public void getBidListUpdateByUnknownIdTest() throws Exception {
    	bidList.setId(5);
		bidList.setAccount("test");
		bidList.setBidQuantity(1);
		bidList.setType("type");
		

		when(bidListService.getBidListById(5)).thenThrow(new NotFoundException("bidList Id : "+5+" not exist"));
		
        mockMvc.perform(get("/bidList/update/5")
        		.param("account","accountTest")
        		.param("type","typeTest")
        		.param("bidQuantity","10")
        		.param("id", "5")
        		.with(csrf()))
                
                .andExpect(status().isOk())
                .andExpect(view().name("errorNotFound"));
    }

    
    @Test
    @WithMockUser(username="user",roles="ADMIN")
    public void postBidToUpdateTest() throws Exception {
       		bidList.setId(1);
    		bidList.setAccount("test");
    		bidList.setBidQuantity(1);
    		bidList.setType("type");
    		
    	when(bidListService.updateBidList(1, bidList)).thenReturn(bidList);
    		
        mockMvc.perform(post("/bidList/update/1")
                .param("account", bidList.getAccount())
                .param("id", String.valueOf(bidList.getId()))
                .param("type", bidList.getType())
                .param("bidQuantity", String.valueOf(bidList.getBidQuantity()))
                .with(csrf()))
              	.andDo(print())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(status().is3xxRedirection());
    }

 

	@Test
    @WithMockUser(username="user",roles="ADMIN")
    public void postBidToUpdateWithErrorTest() throws Exception {
		
        mockMvc.perform(post("/bidList/update/1")
                .param("account", bidList.getAccount())
                .param("type", bidList.getType())
                .param("bidQuantity", String.valueOf(bidList.getBidQuantity()))
                .with(csrf()))
                .andDo(print())
                .andExpect(view().name("bidList/update"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("bidList"))
                .andReturn().getResponse().containsHeader("Account is mandatory");
    }



    @Test
    @WithMockUser(username="user",roles="ADMIN")
    public void getBidDeleteByIdTest() throws Exception {

        mockMvc.perform(get("/bidList/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"));
    }
    
    
}
