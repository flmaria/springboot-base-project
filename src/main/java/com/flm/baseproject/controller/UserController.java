package com.flm.baseproject.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flm.baseproject.dto.PageResult;
import com.flm.baseproject.model.User;
import com.flm.baseproject.security.UserPrincipal;
import com.flm.baseproject.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {
	
	private final UserService userService;
	
	@GetMapping
    public PageResult<User> findAllPageable(@RequestParam(defaultValue = "0") Integer pageIndex, 
            @RequestParam(defaultValue = "50") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean sortAscending) {
		return userService.findAllPageable(pageIndex, pageSize, sortBy, sortAscending);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) {
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
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
		return ResponseEntity.ok().body(this.userService.save(user));
	}

	@DeleteMapping("/{id}")
    public  ResponseEntity<Boolean> deleteUser(@AuthenticationPrincipal UserPrincipal loggedUser, @PathVariable(value = "id") Long userId) {
		userService.deleteById(userId, loggedUser.getId());
		return ResponseEntity.ok().body(true);
    }
	
}
