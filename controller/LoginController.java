package com.nineleaps.authentication.jwt.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nineleaps.authentication.jwt.dto.JwtResponse;
import com.nineleaps.authentication.jwt.dto.RefreshTokenRequest;
import com.nineleaps.authentication.jwt.entity.AuthenticationRequest;

import java.util.Optional;

import com.nineleaps.authentication.jwt.entity.RefreshToken;
import com.nineleaps.authentication.jwt.entity.User;
import com.nineleaps.authentication.jwt.service.IUserService;
import com.nineleaps.authentication.jwt.service.JwtUserDetailsService;
import com.nineleaps.authentication.jwt.service.RefreshTokenService;
import com.nineleaps.authentication.jwt.util.JwtUtils;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

@CrossOrigin

@Getter@Setter
@RestController
@RequestMapping(value = "/api/v1")
public class LoginController {

  
	 @Autowired
	    private AuthenticationManager authenticationManager;
	    @Autowired
	    private JwtUserDetailsService userDetailsService;
	    @Autowired
	    private JwtUtils jwtUtils;
	    @Autowired
	    private IUserService userService;
	    @Autowired
	    private RefreshTokenService refreshTokenService;
	 

	    @PostMapping(value = "/login", produces = "application/json")
	    @ApiOperation("Login into the Application using Email and Password ")

	    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest loginRequest, HttpServletResponse response) throws IOException {
	        try {
	            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserMail(), loginRequest.getUserPassword()));
	        } catch (BadCredentialsException ex) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
	        }

	        User user = userService.findUserByEmail(loginRequest.getUserMail());
	        if (user != null) {
	            RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginRequest.getUserMail());
	            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUserMail());
	            String accessToken = jwtUtils.generateToken(userDetails, response);

	            JwtResponse jwtResponse = new JwtResponse();
	            jwtResponse.setAccessToken(accessToken);
	            jwtResponse.setRefreshToken(refreshToken.getToken());
	            return ResponseEntity.ok(jwtResponse);
	        }
	        return ResponseEntity.notFound().build();	 
	        }
	
	    @PostMapping("/refreshToken")
	    @ApiOperation(value = "Api to refresh jwt token when it expires")
	    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest, HttpServletResponse response) {
	        Optional<RefreshToken> optionalRefreshToken = refreshTokenService.findByToken(refreshTokenRequest.getToken());

	        if (optionalRefreshToken.isPresent()) {
	            RefreshToken refreshToken = optionalRefreshToken.get();

	            if (refreshTokenService.verifyExpiration(refreshToken).isPresent()) {
	                UserDetails userDetails = userDetailsService.loadUserByUsername(refreshToken.getUser().getUserMail());
	                String accessToken = null;
	                try {
	                    accessToken = jwtUtils.generateToken(userDetails, response);
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	                return new JwtResponse(accessToken, refreshTokenRequest.getToken(), null);
	            }
	        }

	        // Handle the case when the refresh token is not found or expired
	        throw new RuntimeException("Refresh token is not in the database or has expired!");
	    }	 
	    
	    
}



