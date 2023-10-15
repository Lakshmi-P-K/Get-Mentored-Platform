package com.nineleaps.authentication.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nineleaps.authentication.jwt.dto.MenteeDTO;
import com.nineleaps.authentication.jwt.dto.MentorDTO;
import com.nineleaps.authentication.jwt.entity.User;
import com.nineleaps.authentication.jwt.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
	@Autowired
	
	private final UserService userService;

	public ProfileController(UserService userService) {
		this.userService = userService;
	}
	
	 @PutMapping("/mentee")
	 @ApiOperation(value = "Update Mentee Profile", notes = "Updates the profile of a Mentee")
	
	 	public String updateUser(@RequestBody @Valid MenteeDTO menteedto) {
			
		 User user = userService.getUserByMail(menteedto.getUserMail());
			System.out.println(user.getPhoneNumber());
			user.setBio(menteedto.getBio());
			user.setMentoringRequiredFor(menteedto.getMentoringRequiredFor());
			user.setRoles(menteedto.getRoles());
			user.setLocation(menteedto.getLocation());
			user.setUserName(menteedto.getUserName());
			user.setPhoneNumber(menteedto.getPhoneNumber());
			userService.updateUser(user);
			return "mentee profile updated";
	}

	    
	 @PutMapping("/mentor")
	 @ApiOperation(value = "Update Mentor Profile", notes = "Updates the profile of a Mentor")
	

	 public String updateUser(@RequestBody MentorDTO mentordto ) {
		 
			User user = userService.getUserByMail(mentordto.getUserMail());
			user.setBio(mentordto.getBio());
			user.setExpertise(mentordto.getExpertise());
	
			
			user.setIndustry(mentordto.getIndustry());
			user.setRoles(mentordto.getRoles());
	    	user.setPhoneNumber(mentordto.getPhoneNumber());
			user.setLocation(mentordto.getLocation());
			user.setChargePerHour(mentordto.getChargePerHour());
			user.setUserName(mentordto.getUserName());
			userService.updateUser(user);
			return "mentor profile updated";
		}

	}


