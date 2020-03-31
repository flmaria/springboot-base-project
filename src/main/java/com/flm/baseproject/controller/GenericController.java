package com.flm.baseproject.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flm.baseproject.model.Generic;
import com.flm.baseproject.exception.ResourceNotFoundException;
import com.flm.baseproject.model.User;
import com.flm.baseproject.service.GenericService;
import com.flm.baseproject.service.UserService;


@RestController
@RequestMapping("time-zones")
public class GenericController {
	
	@Autowired
	private GenericService genericService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping
    public List<Generic> getAllGenerics(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
        
		User user = userService.findByLogin(principal.getName())
			       .orElseThrow(() -> new ResourceNotFoundException("User", "login", principal.getName()));
		
		return genericService.findAllByUserIdAndOrderByNameAsc(user.getId());
    }

	@GetMapping("/{id}")
    public ResponseEntity<Generic> getGenericById(HttpServletRequest request, @PathVariable(value = "id") Long genericId) throws ResourceNotFoundException {
		Principal principal = request.getUserPrincipal();
        
		User user = userService.findByLogin(principal.getName())
			       .orElseThrow(() -> new ResourceNotFoundException("User", "login", principal.getName()));
		
		return this.genericService.findByIdAndUserId(genericId, user.getId()).map(record -> ResponseEntity.ok().body(record))
    			.orElse(ResponseEntity.notFound().build());
    }
    
	@PostMapping
    public Generic createGeneric(HttpServletRequest request, @Valid @RequestBody Generic timeZone) {
		Principal principal = request.getUserPrincipal();
        
		User user = userService.findByLogin(principal.getName())
			       .orElseThrow(() -> new ResourceNotFoundException("User", "login", principal.getName()));
		timeZone.setUser(user);
		
		return genericService.save(timeZone);
    }

	@PutMapping("/{id}")
    public ResponseEntity<Generic> updateGeneric(HttpServletRequest request, @PathVariable(value = "id") Long genericId,
         @Valid @RequestBody Generic timeZone) throws ResourceNotFoundException {
		Principal principal = request.getUserPrincipal();
        
		User user = userService.findByLogin(principal.getName())
			       .orElseThrow(() -> new ResourceNotFoundException("User", "login", principal.getName()));
		
		
		return genericService.findByIdAndUserId(genericId, user.getId()).map(record -> {
			record.setName(timeZone.getName());
			record.setUser(user);
			
			Generic updated = genericService.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
		
    }

	@DeleteMapping("/{id}")
    public Map<String, Boolean> deleteGeneric(HttpServletRequest request, @PathVariable(value = "id") Long genericId)
         throws ResourceNotFoundException {
		Principal principal = request.getUserPrincipal();
        
		User user = userService.findByLogin(principal.getName())
			       .orElseThrow(() -> new ResourceNotFoundException("User", "login", principal.getName()));
		
		Generic generic = genericService.findByIdAndUserId(genericId, user.getId())
       .orElseThrow(() -> new ResourceNotFoundException("Generic", "genericId", genericId));

		genericService.deleteById(generic.getId());
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
	
	@PostMapping("/search")
    public List<Generic> createGeneric(HttpServletRequest request, @Valid @RequestBody String search) {
		Principal principal = request.getUserPrincipal();
        
		User user = userService.findByLogin(principal.getName())
			       .orElseThrow(() -> new ResourceNotFoundException("User", "login", principal.getName()));
		
		
		return genericService.searchByUserIdAndOrderByNameAsc(user.getId(), search);
    }
	
}
