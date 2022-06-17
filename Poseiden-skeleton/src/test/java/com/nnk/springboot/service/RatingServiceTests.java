package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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
public class RatingServiceTests {

	@Mock
	private RatingRepository ratingRepository;

	private Rating rating = new Rating();
	private Rating rating2 = new Rating();

	@InjectMocks
	private RatingService ratingService;

	@Before
	public void init() {
		rating.setId(1);
		rating.setOrderNumber(2);
		rating.setSandpRating("sandpRating");
		rating.setFitchRating("test");

		rating2.setId(1);
		rating2.setOrderNumber(10);
		rating2.setSandpRating("sandpRating");
	}

	@Test
	public void createRatingTest() {
		Mockito.verify(ratingRepository, Mockito.times(0)).save(any());

		ratingService.createRating(rating);

		Mockito.verify(ratingRepository, Mockito.times(1)).save(any());
	}

	@Test
	public void updateRatingTest() throws NotFoundException {
		Mockito.when(ratingRepository.existsById(1)).thenReturn(true);

		Mockito.when(ratingRepository.getOne(1)).thenReturn(rating);

		Rating ratingUpdate = new Rating();
		ratingUpdate.setId(1);
		ratingUpdate.setOrderNumber(10);
		ratingUpdate.setSandpRating("sandpRating");
		ratingUpdate.setFitchRating("test");

		ratingService.updateRating(1, ratingUpdate);
		Rating ratingTest = ratingService.getRatingById(1);

		assertEquals(ratingUpdate.getOrderNumber(), ratingTest.getOrderNumber(), 0);

	}

	@Test(expected = NotFoundException.class)
	public void updateRatingTestThrowsError() throws NotFoundException {
		Mockito.when(ratingRepository.existsById(1)).thenReturn(true);

		Mockito.when(ratingRepository.getOne(1)).thenReturn(rating);

		Rating ratingUpdate = new Rating();
		ratingUpdate.setId(1);
		ratingUpdate.setOrderNumber(10);
		ratingUpdate.setSandpRating("sandpRating");
		ratingUpdate.setFitchRating("test");

		ratingService.updateRating(5, ratingUpdate);

	}

	@Test
	public void getAllRatingTest() {

		List<Rating> ratingList = new ArrayList<>();
		ratingList.add(rating);
		ratingList.add(rating2);

		Mockito.when(ratingRepository.findAll()).thenReturn(ratingList);

		ratingService.createRating(rating);
		ratingService.createRating(rating2);

		List<Rating> allRating = ratingService.getAllRating();
		assertEquals(allRating.size(), 2);
	}

	@Test
	public void deleteRatingTest() {
		Mockito.verify(ratingRepository, Mockito.times(0)).save(any());

		ratingService.createRating(rating);

		Mockito.verify(ratingRepository, Mockito.times(1)).save(any());

		ratingService.deleteRating(1);

		Mockito.verify(ratingRepository, Mockito.times(1)).deleteById(any());
	}

	@Test(expected = NotFoundException.class)
	public void getRatingByIdNotExistTest() throws NotFoundException {

		assertEquals(ratingService.getRatingById(1), 0);

	}
}
