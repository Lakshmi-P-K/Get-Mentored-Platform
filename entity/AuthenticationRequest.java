package com.nineleaps.authentication.jwt.entity;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String userMail;
    private String userPassword;
    

    public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


}
