package com.kp.rating.services;

import java.util.List;

import com.kp.rating.entities.Rating;

//@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

//	Create
	Rating create(Rating rating);

//	Get All Ratings
	List<Rating> getRatings();

//	Get Single User
	List<Rating> getRatingByUserId(String userId);

//	Get All By Hotel
	List<Rating> getRatingByHotelId(String hotelId);
}
