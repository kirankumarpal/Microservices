package com.kp.user.service.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kp.user.service.entities.Hotel;
import com.kp.user.service.entities.Rating;
import com.kp.user.service.entities.User;
import com.kp.user.service.exceptions.ResourceNotFoundException;
import com.kp.user.service.repositories.UserRepository;
import com.kp.user.service.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {
		// Generate unique userId
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		User user = userRepository.findById(userId).orElseThrow(
				() -> new ResourceNotFoundException("User with given id is not found of: " + userId + " !!!!!"));
//		http://localhost:8084/ratings/users/cd2ee97c-75de-4f92-a7dd-9408e8157e62
		Rating[] ratingsOfUser = restTemplate.getForObject("http://localhost:8084/ratings/users/" + user.getUserId(),
				Rating[].class);
//		logger.info("{}", ratingsOfUser);

		List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
		
		List<Rating> ratingList = ratings.stream().map(rating -> {
//			http://localhost:8083/hotels/0185c9aa-df5b-4c1e-beb7-8ac9080fb8cc
			ResponseEntity<Hotel> forEntity = restTemplate
					.getForEntity("http://localhost:8083/hotels/" + rating.getHotelId(), Hotel.class);
			Hotel hotel = forEntity.getBody();
			logger.info("response Status Code {}", forEntity.getStatusCode());
			rating.setHotel(hotel);
			return rating;
		}).collect(Collectors.toList());
		user.setRatings(ratingList);
		return user;
	}

}