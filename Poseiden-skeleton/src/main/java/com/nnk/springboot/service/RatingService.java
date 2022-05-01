package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@Service
public class RatingService {
	
	@Autowired
	private RatingRepository ratingRepository;
	

	private Logger log = LoggerFactory.getLogger(RatingService.class);
	
	
	
	public Rating createRating(Rating Rating) {
		log.info("Create new Curve Point");
		Rating createNewRating = new Rating();
		createNewRating.setOrderNumber(Rating.getOrderNumber());
		createNewRating.setFitchRating(Rating.getFitchRating());
		createNewRating.setSandpRating(Rating.getSandpRating());
		createNewRating.setMoodysRating(Rating.getMoodysRating());
		return ratingRepository.save(createNewRating);
	
	}
}
