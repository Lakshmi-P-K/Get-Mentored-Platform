package com.nineleaps.authentication.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nineleaps.authentication.jwt.entity.ProfileImage;
import com.nineleaps.authentication.jwt.entity.User;
import com.nineleaps.authentication.jwt.service.ProfileService;
import com.nineleaps.authentication.jwt.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserController {

	@Autowired
    private UserService userService;

	@Autowired
	private ProfileService profileService;
	
    @PostMapping({"/api/v1/signUp"})
    @ApiOperation("Register New User in the Platform")
    public User registerNewUser(@RequestBody User user) {
    	 if (userService.isEmailExists(user.getUserMail())) {
 	        throw new IllegalArgumentException("Email ID already exists");
 	    }
        return userService.registerNewUser(user);

    }

    @PutMapping("/api/v1/profileImage")
    @ApiOperation("Upload Profile Image to your profile")
    public String uploadImage(@RequestParam String userMail, @RequestBody ProfileImage request) throws NotFoundException {
        String base64Image = request.getProfileImage();

        System.out.println("base64Image: " + base64Image);
        // Remove all characters except A-Z, a-z, 0-9, +, /, and =
        String cleanBase64Image = base64Image.replaceAll("[^A-Za-z0-9+/=]", "");
        System.out.println("cleanBase64Image: " + cleanBase64Image);
        byte[] imageBytes = Base64Utils.decodeFromString(cleanBase64Image);
        System.out.println(imageBytes);
        profileService.uploadImage(userMail, imageBytes);
        return "Image uploaded successfully";
    }


    @GetMapping("/api/v1/getUserById")
    @ApiOperation("Get User Details By User Id")

    public User getbyid(@RequestParam Long userId){
        return userService.getUserById(userId);
    }
    
    

    


}
