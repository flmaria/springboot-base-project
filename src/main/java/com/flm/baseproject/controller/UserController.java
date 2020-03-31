package com.flm.baseproject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flm.baseproject.exception.ResourceNotFoundException;
import com.flm.baseproject.model.Profile;
import com.flm.baseproject.model.User;
import com.flm.baseproject.service.ProfileService;
import com.flm.baseproject.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping
    public List<User> getAllUsers() {
		return userService.listAll();
    }

	@GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
		return userService.findById(userId).map(record -> {
			record.setPassword("password");
			return ResponseEntity.ok().body(record);
		}).orElse(ResponseEntity.notFound().build());
    }
    
	@PostMapping
    public User createUser(@Valid @RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userService.save(user);
    }

	@PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
         @Valid @RequestBody User user) throws ResourceNotFoundException {
		return userService.findById(userId).map(record -> {
			record.setName(user.getName());
			record.setLogin(user.getLogin());
			record.setEmail(user.getEmail());
			
			if ((user.getPassword() != null && !user.getPassword().trim().equals("")) ||  user.getPassword().trim().equals("password")) {
				record.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			
			record.setProfile(user.getProfile());
			
			User updated = userService.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
		
    }

	@DeleteMapping("/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)
         throws ResourceNotFoundException {
		User user = userService.findById(userId)
       .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		
		userService.deleteById(user.getId());
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
	
	@GetMapping("/profiles")
    public List<Profile> getAllProfiles() throws ResourceNotFoundException {
		return this.profileService.findAll();
    }
	
	
	

}
