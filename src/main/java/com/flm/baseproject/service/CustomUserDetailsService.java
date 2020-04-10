package com.flm.baseproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flm.baseproject.model.User;
import com.flm.baseproject.security.UserPrincipal;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	
	@Autowired
	private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
    	
        User user = this.userService.findByLoginOrEmail(usernameOrEmail);
        
        if (user == null)
        	throw new UsernameNotFoundException("User not found with login or email : " + usernameOrEmail);
    	
    	return UserPrincipal.create(user);
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
    	User user = this.userService.findById(id);
    	
    	if (user == null)
    		throw new UsernameNotFoundException("User not found with id : " + id);
    	
    	return UserPrincipal.create(user);
    }
	
}
