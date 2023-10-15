package com.nineleaps.authentication.jwt.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.nineleaps.authentication.jwt.dto.JwtResponse;
import com.nineleaps.authentication.jwt.dto.SSOUserDto;
import com.nineleaps.authentication.jwt.entity.RefreshToken;
import com.nineleaps.authentication.jwt.entity.User;
import com.nineleaps.authentication.jwt.service.IUserService;
import com.nineleaps.authentication.jwt.service.JwtUserDetailsService;
import com.nineleaps.authentication.jwt.service.RefreshTokenService;
import com.nineleaps.authentication.jwt.util.JwtUtils;

import io.swagger.annotations.ApiOperation;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;


@RestController
public class SsoController {
    @Autowired
    private IUserService ssoService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    
    @Autowired
    private RefreshTokenService refreshTokenService;
  
    @PostMapping(value = "/api/v1/ssoUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Register and Login using Single Sign On with Google")
    @ResponseBody
    public ResponseEntity<?> insertSsoUser(@RequestBody SSOUserDto newSsoUser,HttpServletResponse response) throws IOException {
        User existingUser = ssoService.findUserByEmail(newSsoUser.getUserMail());
        if (existingUser != null) {
        	RefreshToken refreshToken = refreshTokenService.createRefreshToken(newSsoUser.getUserMail());
            
        	UserDetails userDetails = userDetailsService.loadUserByUsername(existingUser.getUserMail());
            String accessToken =  jwtUtils.generateToken(userDetails,response);
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setAccessToken(accessToken);
            jwtResponse.setRefreshToken(refreshToken.getToken());
            return ResponseEntity.ok(jwtResponse);
        } else {
            User registeredUser = ssoService.insertUser(newSsoUser); // Assuming the method name is "insertUser"
       
            return ResponseEntity.ok("Registered Successfully") ;
        }
    
    }}