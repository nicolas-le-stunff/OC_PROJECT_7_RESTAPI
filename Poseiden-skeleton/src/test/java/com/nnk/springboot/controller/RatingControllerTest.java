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

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;

import javassist.NotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(RatingController.class)
public class RatingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RatingService ratingService;

	@MockBean
	private RatingRepository ratingRepository;

	private Rating rating = new Rating();

	@Before
	public void addCurve() {
		rating.setOrderNumber(1);
		rating.setFitchRating("2");
		rating.setSandpRating("3");
		rating.setMoodysRating("4");
		ratingService.createRating(rating);
	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void getRatingTest() throws Exception {

		mockMvc.perform(get("/rating/list")).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("rating/list"));
	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void getRatingAddTest() throws Exception {
		mockMvc.perform(get("/rating/add")).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("rating/add")).andExpect(model().attributeExists("rating"));

	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void postRatingTest() throws Exception {
		rating.setOrderNumber(1);
		rating.setFitchRating("2");
		rating.setSandpRating("3");
		rating.setMoodysRating("4");

		mockMvc.perform(post("/rating/validate").param("orderNumber", String.valueOf(rating.getOrderNumber()))
				.param("sandpRating", String.valueOf(rating.getSandpRating()))
				.param("moodysRating", String.valueOf(rating.getMoodysRating()))
				.param("fitchRating", String.valueOf(rating.getFitchRating())).with(csrf())).andDo(print())
				.andExpect(view().name("redirect:/rating/list")).andExpect(status().is3xxRedirection());

	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void postRatingWithErrorTest() throws Exception {
		mockMvc.perform(post("/rating/validate").param("orderNumber", String.valueOf(rating.getOrderNumber()))
				.param("sandpRating", String.valueOf(rating.getSandpRating()))
				.param("moodysRating", String.valueOf(rating.getMoodysRating())).with(csrf())).andDo(print())
				.andExpect(view().name("rating/add")).andExpect(status().is2xxSuccessful());

	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void getRatingUpdateByExistingIdTest() throws Exception {
		rating.setOrderNumber(1);
		rating.setFitchRating("2");
		rating.setSandpRating("3");
		rating.setMoodysRating("4");

		when(ratingService.getRatingById(1)).thenReturn(rating);

		mockMvc.perform(get("/rating/update/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("rating/update")).andExpect(model().attributeExists("rating"));
	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void getRatingUpdateByUnknownIdTest() throws Exception {
		rating.setOrderNumber(1);
		rating.setFitchRating("2");
		rating.setSandpRating("3");
		rating.setMoodysRating("4");

		when(ratingService.getRatingById(5)).thenThrow(new NotFoundException("rating Id : " + 5 + " not exist"));

		mockMvc.perform(get("/rating/update/5").param("orderNumber", String.valueOf(rating.getOrderNumber()))
				.param("sandpRating", String.valueOf(rating.getSandpRating()))
				.param("moodysRating", String.valueOf(rating.getMoodysRating()))
				.param("fitchRating", String.valueOf(rating.getFitchRating())).with(csrf())).andExpect(status().isOk())
				.andExpect(view().name("errorNotFound"));
	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void postRatingUpdateTest() throws Exception {
		rating.setOrderNumber(1);
		rating.setFitchRating("2");
		rating.setSandpRating("3");
		rating.setMoodysRating("4");

		when(ratingService.updateRating(1, rating)).thenReturn(rating);

		mockMvc.perform(post("/rating/update/1").param("orderNumber", String.valueOf(rating.getOrderNumber()))
				.param("sandpRating", String.valueOf(rating.getSandpRating()))
				.param("moodysRating", String.valueOf(rating.getMoodysRating()))
				.param("fitchRating", String.valueOf(rating.getFitchRating())).with(csrf())).andDo(print())
				.andExpect(view().name("redirect:/rating/list")).andExpect(status().is3xxRedirection());
	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void postRatingUpdateWithErrorTest() throws Exception {

		mockMvc.perform(post("/rating/update/1").param("orderNumber", String.valueOf(rating.getOrderNumber()))
				.param("sandpRating", String.valueOf(rating.getSandpRating()))
				// .param("moodysRating", String.valueOf(rating.getMoodysRating()))
				.param("fitchRating", String.valueOf(rating.getFitchRating())).with(csrf())).andDo(print())
				.andExpect(view().name("redirect:/rating/update")).andExpect(status().is3xxRedirection());

	}

	@Test
	@WithMockUser(username = "user", roles = "ADMIN")
	public void getRatingDeleteByIdTest() throws Exception {

		mockMvc.perform(get("/rating/delete/1")).andDo(print()).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/rating/list"));
	}
}
