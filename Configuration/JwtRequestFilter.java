package com.nineleaps.authentication.jwt.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.nineleaps.authentication.jwt.entity.User;
import com.nineleaps.authentication.jwt.service.JwtUserDetailsService;
import com.nineleaps.authentication.jwt.util.JwtUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private JwtUserDetailsService userDetailsService;

  
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);

            try {

            	if (jwtUtils.validateToken(jwt)) {
            	    System.out.println(jwt);
            	    String userMail = jwtUtils.extractEmail(jwt);
            	    System.out.println(userMail);
            	    String phoneNumber = jwtUtils.extractPhoneNumber(jwt);
            	    System.out.println(phoneNumber);

            	    // Check if userMail and phoneNumber are not null or empty
            	    
            	        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userMail);
            	        System.out.println("load");

            	        // Create a new instance of User and set the phone number
            	        User customUserDetails = new User(userMail, phoneNumber, null);
            	        customUserDetails.getId();
            	        System.out.println("mailll");
            	        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            	        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            	        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            	 
            	        // Handle the case when userMail or phoneNumber is null or empty
            	     //   response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user details");
            	    
            	} else {
            	    // Token validation failed, handle the error
            	    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            	    return;
            	
            }
            } catch (Exception e) {
              e.printStackTrace(); // Print the exception stack trace
              // Log the error message using your preferred logging framework
              logger.error("Exception occurred during token validation: " + e.getMessage());
              // Handle the error accordingly
              response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token validation error");
              return;

            }}
        // Proceed to the next filter in the chain
        chain.doFilter(request, response);
    }
        }



