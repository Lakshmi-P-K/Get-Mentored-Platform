package com.nineleaps.authentication.jwt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nineleaps.authentication.jwt.entity.CustomUserDetails;
import com.nineleaps.authentication.jwt.entity.User;
import com.nineleaps.authentication.jwt.enums.UserRole;
import com.nineleaps.authentication.jwt.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {


	@Autowired
    private UserRepository userRepository;

	    private List<GrantedAuthority> getAuthorities(Set<UserRole> roles) {
	        List<GrantedAuthority> authorities = new ArrayList<>();
	        for (UserRole role : roles) {
	            authorities.add(new SimpleGrantedAuthority(role.name()));
	        }
	        return authorities;
	    }
	    @Override
	    public UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException {
	        User user = userRepository.findByUserMail(userMail);
		        if (user == null) {
		            throw new UsernameNotFoundException("User not found with email: " + userMail);
		        }
	
	        // Create an instance of your custom User class
	        CustomUserDetails userDetails;
		        if (user.getUserPassword() != null && !user.getUserPassword().isEmpty()) {
		            userDetails = new CustomUserDetails(
		                user.getUserMail(),
		                user.getUserPassword(),
		                getAuthorities(user.getRoles())
		            );
		        } 
			        else {
		            userDetails = new CustomUserDetails(
		                user.getUserMail(),
		                getAuthorities(user.getRoles())
		            );
		        }
	
	        // Create an instance of your custom User class
	     
	        userDetails.setUserId(user.getId());
	        System.out.println(userDetails.getUserId());
	        return userDetails;

}}

