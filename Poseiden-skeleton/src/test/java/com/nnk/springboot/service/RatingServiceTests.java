package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;

import javassist.NotFoundException;

import org.junit.Assert;
import org.junit.Before;
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
public class RatingServiceTests {

	@Mock
	private RatingRepository ratingRepository;

	@InjectMocks
	private RatingService ratingService;
	
	@Test
	public void createRatingTest() {
		Mockito.verify(ratingRepository,Mockito.times(0)).save(any());
		
		Rating rating = new Rating();
		rating.setId(1);
		rating.setOrderNumber(2);
		rating.setSandpRating("sandpRating");
		rating.setFitchRating("test");
		
		ratingService.createRating(rating);
		
		Mockito.verify(ratingRepository,Mockito.times(1)).save(any());
		
	}
	
	@Test
	public void updateRatingTest() {	
		Mockito.when(ratingRepository.existsById(1)).thenReturn(true);
		
		
		Rating rating = new Rating();
		rating.setId(1);
		rating.setOrderNumber(2);
		rating.setSandpRating("sandpRating");
		rating.setFitchRating("test");
		
		Mockito.when(ratingRepository.getOne(1)).thenReturn(rating);
		
		
		Rating ratingUpdate = new Rating();
		ratingUpdate.setId(1);
		ratingUpdate.setOrderNumber(10);
		ratingUpdate.setSandpRating("sandpRating");
		ratingUpdate.setFitchRating("test");
		
		
		try {
			ratingService.updateRating(1, ratingUpdate);
			Rating ratingTest = ratingService.getRatingById(1);
			
			assertEquals(ratingUpdate.getOrderNumber(),ratingTest.getOrderNumber(),0);
			
			
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
		
	}
	
	@Test
	public void getAllRatingTest() {
		
	
		Rating rating = new Rating();
		rating.setId(1);
		rating.setOrderNumber(2);
		rating.setSandpRating("sandpRating");
		rating.setFitchRating("test");
		
		Rating rating2 = new Rating();
		rating2.setId(1);
		rating2.setOrderNumber(10);
		rating2.setSandpRating("sandpRating");
		
		List<Rating> ratingList = new ArrayList<>();
		ratingList.add(rating);
		ratingList.add(rating2);
		
		Mockito.when(ratingRepository.findAll()).thenReturn(ratingList);
		
		ratingService.createRating(rating);
		ratingService.createRating(rating2);
		
		List<Rating> allRating = ratingService.getAllRating();	
		assertEquals(allRating.size(),2);
	}
	
/*	@Test
	public void ratingTest() {
		Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

		// Save
		rating = ratingRepository.save(rating);
		Assert.assertNotNull(rating.getId());
		Assert.assertTrue(rating.getOrderNumber() == 10);

		// Update
		rating.setOrderNumber(20);
		rating = ratingRepository.save(rating);
		Assert.assertTrue(rating.getOrderNumber() == 20);

		// Find
		List<Rating> listResult = ratingRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rating.getId();
		ratingRepository.delete(rating);
		Optional<Rating> ratingList = ratingRepository.findById(id);
		Assert.assertFalse(ratingList.isPresent());
	}*/
}
