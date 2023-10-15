package com.nineleaps.authentication.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nineleaps.authentication.jwt.dao.UserDao;
import com.nineleaps.authentication.jwt.entity.User;
import com.nineleaps.authentication.jwt.service.IEmailServices;

import io.swagger.annotations.ApiOperation;

@RestController
public class ForgotPasswordController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String otp;
    
    @Autowired
    public IEmailServices emailServicesImplementation;

    public void OTPController(IEmailServices emailServicesImplementation) {
        this.emailServicesImplementation = emailServicesImplementation;
    }

    @PostMapping("/api/v1/sendOtp")
    @ApiOperation("Receive an OTP for your registered Email Id")
    public ResponseEntity<String> generateOTP(@RequestParam String userEmail) {

        User user=userDao.getUserEmail(userEmail);


        if(user!=null) {
            otp= emailServicesImplementation.generateOTP(userEmail);
            emailServicesImplementation.sendEmail(otp, userEmail);
            return ResponseEntity.ok("DONE ...");
        }else {
            return ResponseEntity.ok("Your are not a valid user");
        }
    }

    @PostMapping("/api/v1/verifyOtp")
    @ApiOperation("verify the OTP sent for the registered Email Id")
    
    public boolean verifyOTP( @RequestParam String userEnteredotp) {
        return emailServicesImplementation.verifyOTP(otp, userEnteredotp);
    }

    @PutMapping("/api/v1/changePassword")
    @ApiOperation("Change the password for your profile bu entering a New Password ")
    
    public ResponseEntity<String> changePassword(@RequestParam("newpass") String newpass,@RequestParam( "email") String email) {

        User user=userDao.getUserEmail(email);

        if(user!=null) {
            user.setUserPassword(this.bCryptPasswordEncoder.encode(newpass));
            this.userDao.save(user);
           return ResponseEntity.ok("password changed");}
        else {
            return ResponseEntity.ok("Your are not a valid user");
        }
    }
}




