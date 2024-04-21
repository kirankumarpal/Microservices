package com.kp.hotel.services;

import java.util.List;

import com.kp.hotel.entities.Hotel;

public interface HotelService {

//	Create
	Hotel create(Hotel hotel);

//	Get All Hotels
	List<Hotel> getAll();

//	Get Single User
	Hotel get(String Id);

}
