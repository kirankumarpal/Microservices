package com.kp.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kp.hotel.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, String> {

}
