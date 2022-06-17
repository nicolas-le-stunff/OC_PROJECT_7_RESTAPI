package com.nnk.springboot.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

import javassist.NotFoundException;

@Service
public class RatingService {

	@Autowired
	private RatingRepository ratingRepository;

	private Logger log = LoggerFactory.getLogger(RatingService.class);

	/**
	 * Get all Rating
	 * 
	 * @return list of Rating
	 */
	public List<Rating> getAllRating() {
		log.info("Get all rating");
		return ratingRepository.findAll();
	}

	/**
	 * Create a new Rating
	 * 
	 * @param Rating
	 * @return Rating
	 */
	public Rating createRating(Rating Rating) {
		log.info("Create new Curve Point");
		Rating createNewRating = new Rating();
		createNewRating.setOrderNumber(Rating.getOrderNumber());
		createNewRating.setFitchRating(Rating.getFitchRating());
		createNewRating.setSandpRating(Rating.getSandpRating());
		createNewRating.setMoodysRating(Rating.getMoodysRating());
		return ratingRepository.save(createNewRating);
	}

	/**
	 * Delete Rating by id
	 * 
	 * @param id
	 */
	@Transactional
	public void deleteRating(Integer id) {
		log.info("Delete rating ID : " + id);
		ratingRepository.deleteById(id);
	}

	/**
	 * Update Rating by ID
	 * 
	 * @param id
	 * @param rating
	 * @return
	 * @throws NotFoundException
	 */
	@Transactional
	public Rating updateRating(Integer id, Rating rating) throws NotFoundException {
		log.info("Update rating");
		if (ratingRepository.existsById(id)) {
			Rating ratingUpdate = ratingRepository.getOne(id);
			ratingUpdate.setFitchRating(rating.getFitchRating());
			ratingUpdate.setMoodysRating(rating.getMoodysRating());
			ratingUpdate.setOrderNumber(rating.getOrderNumber());
			ratingUpdate.setSandpRating(rating.getSandpRating());
			return ratingRepository.save(ratingUpdate);

		}
		log.error("rating Id : " + id + " not exist");
		throw new NotFoundException("rating Id : " + id + " not exist");
	}

	/**
	 * Get rating By Id
	 * 
	 * @param id
	 * @return Rating
	 * @throws NotFoundException
	 */
	public Rating getRatingById(Integer id) throws NotFoundException {
		log.info("Get rating ID :" + id);
		if (ratingRepository.existsById(id)) {
			return ratingRepository.getOne(id);
		}
		log.error("rating Id : " + id + " not exist");
		throw new NotFoundException("rating Id : " + id + " not exist");
	}
}
