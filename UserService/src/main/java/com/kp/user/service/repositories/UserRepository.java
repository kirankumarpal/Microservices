package com.kp.user.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kp.user.service.entities.User;

public interface UserRepository extends JpaRepository<User, String> {

}
