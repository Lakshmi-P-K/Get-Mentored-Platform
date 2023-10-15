package com.nineleaps.authentication.jwt.dto;

import java.util.Set;

import com.nineleaps.authentication.jwt.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MenteeDTO {
	private Long id;
	private Set<UserRole> roles;
	private String userName;
	private String userMail;
	private String phoneNumber;
	private String location;
	private String bio;
	private String mentoringRequiredFor;
	public MenteeDTO() {
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Set<UserRole> getRoles() {
		return roles;
	}
	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getMentoringRequiredFor() {
		return mentoringRequiredFor;
	}
	public void setMentoringRequiredFor(String mentoringRequiredFor) {
		this.mentoringRequiredFor = mentoringRequiredFor;
	}
	public MenteeDTO(Long id, Set<UserRole> roles, String userName, String userMail, String phoneNumber,
			 String location, String bio, String mentoringRequiredFor) {
		super();
		this.id = id;
		this.roles = roles;
		this.userName = userName;
		this.userMail = userMail;
		this.phoneNumber = phoneNumber;
	
		this.location = location;
		this.bio = bio;
		this.mentoringRequiredFor = mentoringRequiredFor;
	}
	
		
	}
