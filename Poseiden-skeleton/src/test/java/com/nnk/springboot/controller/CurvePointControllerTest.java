package com.nnk.springboot.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;

import javassist.NotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(CurveController.class)
public class CurvePointControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CurvePointService curvePointService;

	@MockBean
	private CurvePointRepository curvePointRepository;

	private CurvePoint curvePoint = new CurvePoint();

	@Before
	public void addCurve() {
		curvePoint.setId(1);
		curvePoint.setCurveId(2);
		curvePoint.setTerm(3);
		curvePoint.setValue(4);
		curvePointService.createCurvePoint(curvePoint);
	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void getCurvePointTest() throws Exception {

		mockMvc.perform(get("/curvePoint/list")).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("curvePoint/list"));
	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void getCurvePointAddTest() throws Exception {
		mockMvc.perform(get("/curvePoint/add")).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("curvePoint/add")).andExpect(model().attributeExists("curvePoint"));

	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void postCurvePointTest() throws Exception {
		curvePoint.setId(1);
		curvePoint.setCurveId(2);
		curvePoint.setTerm(3);
		curvePoint.setValue(4);

		mockMvc.perform(post("/curvePoint/validate").param("curveId", String.valueOf(curvePoint.getCurveId()))
				.param("term", String.valueOf(curvePoint.getTerm()))
				.param("value", String.valueOf(curvePoint.getValue())).with(csrf())).andDo(print())
				.andExpect(view().name("redirect:/curvePoint/list")).andExpect(status().is3xxRedirection());

	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void postCurvePointWithErrorTest() throws Exception {
		mockMvc.perform(post("/curvePoint/validate")
				// .param("curveId", String.valueOf(curvePoint.getCurveId()))
				// .param("term", String.valueOf(curvePoint.getTerm()))
				.param("value", String.valueOf(curvePoint.getValue())).with(csrf())).andDo(print())
				.andExpect(view().name("curvePoint/add")).andExpect(status().is2xxSuccessful());

	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void getCurvePointUpdateByExistingIdTest() throws Exception {
		curvePoint.setId(1);
		curvePoint.setCurveId(2);
		curvePoint.setTerm(3);
		curvePoint.setValue(4);

		when(curvePointService.getCurvePointById(1)).thenReturn(curvePoint);

		mockMvc.perform(get("/curvePoint/update/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("curvePoint/update")).andExpect(model().attributeExists("curvePoint"));
	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void getCurvePointUpdateByUnknownIdTest() throws Exception {
		curvePoint.setId(5);
		curvePoint.setCurveId(2);
		curvePoint.setTerm(3);
		curvePoint.setValue(4);

		when(curvePointService.getCurvePointById(5))
				.thenThrow(new NotFoundException("curvePoint Id : " + 5 + " not exist"));

		mockMvc.perform(
				get("/curvePoint/update/5").param("id", "5").param("curveId", String.valueOf(curvePoint.getCurveId()))
						.param("term", String.valueOf(curvePoint.getTerm()))
						.param("value", String.valueOf(curvePoint.getValue())).with(csrf()))
				.andExpect(status().isOk()).andExpect(view().name("errorNotFound"));
	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void postCurvePointUpdateTest() throws Exception {
		curvePoint.setId(1);
		curvePoint.setCurveId(2);
		curvePoint.setTerm(3);
		curvePoint.setValue(4);

		when(curvePointService.updateCurvePoint(1, curvePoint)).thenReturn(curvePoint);

		mockMvc.perform(post("/curvePoint/update/1").param("curveId", String.valueOf(curvePoint.getCurveId()))
				.param("term", String.valueOf(curvePoint.getTerm()))
				.param("value", String.valueOf(curvePoint.getValue())).with(csrf())).andDo(print())
				.andExpect(view().name("redirect:/curvePoint/list")).andExpect(status().is3xxRedirection());
	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void postCurvePointUpdateWithErrorTest() throws Exception {

		mockMvc.perform(post("/curvePoint/update/1")
				// .param("curveId", String.valueOf(curvePoint.getCurveId()))
				// .param("term", String.valueOf(curvePoint.getTerm()))
				.param("value", String.valueOf(curvePoint.getValue())).with(csrf())).andDo(print())
				.andExpect(view().name("curvePoint/update")).andExpect(status().is2xxSuccessful()).andReturn()
				.getResponse().containsHeader("Account is mandatory");
	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void getCurvePointDeleteByIdTest() throws Exception {

		mockMvc.perform(get("/curvePoint/delete/1")).andDo(print()).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/curvePoint/list"));
	}
}
