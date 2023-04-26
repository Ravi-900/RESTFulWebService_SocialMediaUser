package com.rest.webservcies.restfulwebservices.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.webservcies.restfulwebservices.user.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{

}
