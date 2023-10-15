package com.nineleaps.authentication.jwt.service;

public interface IEmailServices {
    public String generateOTP(String userEmail);
    public boolean verifyOTP(String otp, String enteredOTP) ;
    public boolean sendEmail(String otp, String to);


}