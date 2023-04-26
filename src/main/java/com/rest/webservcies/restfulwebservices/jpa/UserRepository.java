package com.rest.webservcies.restfulwebservices.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.webservcies.restfulwebservices.user.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
