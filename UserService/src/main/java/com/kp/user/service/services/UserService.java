package com.kp.user.service.services;

import java.util.List;

import com.kp.user.service.entities.User;

public interface UserService {

//	Create
	User saveUser(User user);

//	Get All User
	List<User> getAllUser();

//	Get Single User
	User getUser(String userId);
}
