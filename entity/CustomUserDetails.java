package com.nineleaps.authentication.jwt.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;



public class CustomUserDetails extends org.springframework.security.core.userdetails.User {


	private Long userId; // Add a field for user ID

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    	
        super(username, password, authorities);
    	}
    

    public CustomUserDetails(String userMail,List<GrantedAuthority> authorities) {
        super(userMail,userMail, buildAuthoritiesSet(authorities));
    }
    
  

    private static Set<GrantedAuthority> buildAuthoritiesSet(List<GrantedAuthority> authorities) {
        return new HashSet<>(authorities);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
