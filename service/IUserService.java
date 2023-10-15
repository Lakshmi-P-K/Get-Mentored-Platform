package com.nineleaps.authentication.jwt.service;

import com.nineleaps.authentication.jwt.dto.SSOUserDto;
import com.nineleaps.authentication.jwt.entity.User;
public interface IUserService {
	
	public User getUserViaPhoneNumber(String phoneNumber);
    public User updateUser(User user); 
	public User getUserByMail(String userMai);
	User insertuser(SSOUserDto newSsoUser);
	public User findUserByEmail(String userMail);
    public User insertUser(SSOUserDto newSsoUser);


}
