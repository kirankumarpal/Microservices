package com.kp.hotel.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kp.hotel.entities.Hotel;
import com.kp.hotel.exceptions.ResourceNotFoundException;
import com.kp.hotel.services.HotelService;

@RestController
@RequestMapping("/hotels")
public class HotelController {

	@Autowired
	private HotelService hotelService;

//	Create
	@PostMapping
	public ResponseEntity<Hotel> create(@RequestBody Hotel hotel) {
		Hotel hotel1 = hotelService.create(hotel);
		return ResponseEntity.status(HttpStatus.CREATED).body(hotel1);
	}

//	Get All User
	@GetMapping
	public ResponseEntity<List<Hotel>> getAll() {
		List<Hotel> allUser = hotelService.getAll();
		return ResponseEntity.ok(allUser);
	}

//	Single User Get
//	@GetMapping("/{id}")
//	public ResponseEntity<Hotel> get(@PathVariable String id) {
////		Hotel hotel = hotelService.get(id);
//		return ResponseEntity.ok(hotelService.get(id));
//	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Hotel> get(@PathVariable String id) {
	    try {
	        Hotel hotel = hotelService.get(id);
	        return ResponseEntity.ok(hotel);
	    } catch (ResourceNotFoundException ex) {
	        return ResponseEntity.notFound().build(); // Return 404 if hotel is not found
	    }
	}


}
