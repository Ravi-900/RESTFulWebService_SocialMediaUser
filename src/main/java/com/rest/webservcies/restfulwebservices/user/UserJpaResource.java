package com.rest.webservcies.restfulwebservices.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rest.webservcies.restfulwebservices.jpa.PostRepository;
import com.rest.webservcies.restfulwebservices.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {
	
	private UserRepository userRepository;
	private PostRepository postRepository;
	
	public UserJpaResource(UserRepository userRepository,PostRepository postRepository) {
		super();
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}


	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers(){
		List<User> users = userRepository.findAll();
		
		return users;
	}
	
	
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id){
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("id: "+id);
		}
		
		EntityModel<User> entityModel = EntityModel.of(user.get());
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	@PostMapping("/jpa/users")
	public  ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userId}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id){
		userRepository.deleteById(id);
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrievePostsForUser(@PathVariable int id){
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("id: "+id);
		}
		return user.get().getPosts();
	}
	
	@GetMapping("/jpa/users/{id}/posts/{postId}")
	public EntityModel<Post> retrieveOnePostForUser(@PathVariable int id,@PathVariable int postId){
		Optional<User> optionalUser = userRepository.findById(id);
		
		if(optionalUser.isEmpty()) {
			throw new UserNotFoundException("id: "+id);
		}
		
		Optional<Post> optionalPost = postRepository.findById(postId);
		
		if(optionalPost.isEmpty()) {
			throw new PostNotFoundException("postId: "+postId);
		}
		
		EntityModel<Post> entityModel = EntityModel.of(optionalPost.get());
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrievePostsForUser(id));
		entityModel.add(link.withRel("all-user-posts"));
		
		return entityModel;
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<User> createPostForUser(@PathVariable int id,@Valid @RequestBody Post post){
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("id: "+id);
		}
		
		post.setUser(user.get());

		Post savedPost = postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userId}").buildAndExpand(savedPost.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/jpa/users/{id}/posts/{postId}")
	public void deleteUserPostById(@PathVariable int id,@PathVariable int postId){
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("id: "+id);
		}
		
		postRepository.deleteById(postId);
	}
	
}
