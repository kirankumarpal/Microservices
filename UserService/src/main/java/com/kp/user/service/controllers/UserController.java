package com.kp.user.service.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kp.user.service.entities.User;
import com.kp.user.service.services.UserService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	// Create
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User user1 = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}

	// Get All User
	@GetMapping
	public ResponseEntity<List<User>> getAllUser() {
		List<User> allUser = userService.getAllUser();
		return ResponseEntity.ok(allUser);
	}

	int retryCount = 1;

	// Single User Get
	@GetMapping("/{userId}")
//	@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingFallback")
//	@Retry(name = "ratingHotelService", fallbackMethod = "ratingFallback")
	@RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
		logger.info("Get Single User Handler: User Controller");
		logger.info("Retry Count: {}", retryCount);
		retryCount++;
		User user = userService.getUser(userId);
		if (user == null) {
			return ResponseEntity.notFound().build(); // Return 404 if user is not found
		}
		return ResponseEntity.ok(user);
	}

	// Creating fallback method for circuit breaker
	public ResponseEntity<User> ratingFallback(String userId, Exception ex) {
		logger.info("Fallback is executed because the rating service is down : ", ex.getMessage());
		ex.printStackTrace();
		User user = User.builder().email("dummy@gmail.com").name("Dummy")
				.about("This user is created dummy because the rating service is down").userId("141234").build();
		return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
	}

}
