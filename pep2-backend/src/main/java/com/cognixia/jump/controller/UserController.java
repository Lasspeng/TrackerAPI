package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.Title;
import com.cognixia.jump.model.User;
import com.cognixia.jump.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserService userService;
	
	/********************
		GET OPERATIONS
	 ********************/
	@GetMapping("/users")
	public ResponseEntity<?> getUsers(){
		
		List<User> users = userService.getAllUsers();
		
		return ResponseEntity.status(200).body(users);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUserById(@PathVariable String id) throws Exception{
		
		User user = userService.getUserById(id);
		
		return ResponseEntity.status(200).body(user);
	}

	
	/********************
		POST OPERATIONS
	 ********************/
	
	@PostMapping("/user/auth")
	public ResponseEntity<?> getUserByCredentials(@RequestBody User user) throws Exception{
		
		User validUser = userService.getUserByCredentials(user.getUsername(), user.getPassword());
		
		return ResponseEntity.status(200).body(validUser);
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> createUser(@RequestBody User user) throws Exception{
		
		User created = userService.createUser(user);
		
		return ResponseEntity.status(201).body(created);
	}

	@PostMapping("/users/count")
	public ResponseEntity<?> getCompletedCount(@RequestBody Title title) {

		return ResponseEntity.status(200).body(userService.getGlobalShowCompletedCount(title.getTitle()));
	} 

	@PostMapping("/users/rating")
	public ResponseEntity<AggregationResults<Object>> getAverageRating(@RequestBody Title title) {

		userService.getShowsAverageRating(title.getTitle());

		return ResponseEntity.status(200).build();
	}
	
	/********************
		UPDATE OPERATIONS
	 ********************/
	@PatchMapping("/user/{id}") 
	public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User user ) throws Exception{
		
		User updated = userService.updateUser(id, user);
		
		return ResponseEntity.status(201).body(updated);
	}
	
	/********************
		DELETE OPERATIONS
	 ********************/
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable String id) throws Exception{
		
		User deleted = userService.deleteUser(id);
		
		return ResponseEntity.status(201).body(deleted);
	}
	
	
}
