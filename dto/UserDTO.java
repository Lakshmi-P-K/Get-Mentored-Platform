package com.nineleaps.authentication.jwt.dto;

public class UserDTO {
 private Long id;
 private String userName;
 private String userMail;
	private String phoneNumber;
 private byte[] profileImage;
	private String expertise;
	
	private String location;
	
	private String bio;
	
	private String industry;
	
	private String mentoringRequiredFor;
 private double chargePerHour;

 private Double averageRating;
	public Long getId() {
 return id;
	}
	public void setId(Long id) {
 this.id = id;
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
	
	public byte[] getProfileImage() {
 return profileImage;
	}
	public void setProfileImage(byte[] profileImage) {
 this.profileImage = profileImage;
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
	public String getMentoringRequiredFor() {
 return mentoringRequiredFor;
	}
	public void setMentoringRequiredFor(String mentoringRequiredFor) {
 this.mentoringRequiredFor = mentoringRequiredFor;
	}
	public double getChargePerHour() {
 return chargePerHour;
	}
	public void setChargePerHour(double chargePerHour) {
 this.chargePerHour = chargePerHour;
	}
	public Double getAverageRating() {
 return averageRating;
	}
	public void setAverageRating(Double averageRating) {
 this.averageRating = averageRating;
	}
 
}