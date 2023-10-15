package com.nineleaps.authentication.jwt.dto;

import java.util.Set;
import com.nineleaps.authentication.jwt.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter

public class MentorDTO {
	private Long id;
	private Set<UserRole> roles;
	private String userName;
	private String userMail;
	private String phoneNumber;
	private byte[] profileImage;
	 
   
	private String expertise;
	private String location;
	private String bio;
	private String industry;
	private double chargePerHour;
	
	private Double averageMentorRating;
    private Double averageMenteeRating;
	public MentorDTO() {
		
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
	 
	
	
	public Double getAverageMentorRating() {
		return averageMentorRating;
	}
	public void setAverageMentorRating(Double averageMentorRating) {
		this.averageMentorRating = averageMentorRating;
	}
	public Double getAverageMenteeRating() {
		return averageMenteeRating;
	}
	public void setAverageMenteeRating(Double averageMenteeRating) {
		this.averageMenteeRating = averageMenteeRating;
	}
	
	public byte[] getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
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
    
	
	public double getChargePerHour() {
		return chargePerHour;
	}
	public void setChargePerHour(double chargePerHour) {
		this.chargePerHour = chargePerHour;
	}
	public String getExpertise() {
		return expertise;
	}
	public void setExpertise(String expertise) {
		this.expertise = expertise;
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
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public MentorDTO(Long id, Set<UserRole> roles, String userName, String userMail, String phoneNumber,
			byte[] profileImage, String expertise, String location, String bio, String industry, double chargePerHour,
			Double averageMentorRating, Double averageMenteeRating) {
		super();
		this.id = id;
		this.roles = roles;
		this.userName = userName;
		this.userMail = userMail;
		this.phoneNumber = phoneNumber;
		this.profileImage = profileImage;
		this.expertise = expertise;
		this.location = location;
		this.bio = bio;
		this.industry = industry;
		this.chargePerHour = chargePerHour;
		this.averageMentorRating = averageMentorRating;
		this.averageMenteeRating = averageMenteeRating;
	}
	
	
	
	
	
	

	}
