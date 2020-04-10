package com.flm.baseproject.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flm.baseproject.exception.ResourceNotFoundException;
import com.flm.baseproject.model.User;
import com.flm.baseproject.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("users")
public class UserController {
	
	private final UserService userService;
	
	@GetMapping
    public List<User> findAllOrderByNameAsc() {
		return userService.findAllOrderByNameAsc();
    }

	@GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
		User user = this.userService.findById(userId);
		
		if (user == null)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok().body(user);
    }
    
	@PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		return ResponseEntity.ok().body(this.userService.save(user));
    }

	@PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) throws ResourceNotFoundException {
		return ResponseEntity.ok().body(this.userService.save(user));
	}

	@DeleteMapping("/{id}")
    public  ResponseEntity<Boolean> deleteUser(@PathVariable(value = "id") Long userId) {
		userService.deleteById(userId);
		return ResponseEntity.ok().body(true);
    }
	
}
