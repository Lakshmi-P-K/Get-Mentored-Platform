package com.nineleaps.authentication.jwt.entity;


import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import com.nineleaps.authentication.jwt.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
@Getter @Setter
@Entity
public class User {
	
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
		
	 	private String userName;
	 	
	 	@Email	 
	    private String userMail;
	 	
		private String phoneNumber;
		
	    private String userPassword;
	    
	    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
	    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
	    @Enumerated(EnumType.STRING)
	    private Set<UserRole> roles;
	    
	    @Lob
	    private byte[] profileImage;
	    
		private String expertise;
		
		private String location;
		
		private String bio;
		
		private String industry;
		
		private String mentoringRequiredFor;
		 private double chargePerHour;

		   
		    public double getChargePerHour() {
		        return chargePerHour;
		    }

		    public void setChargePerHour(double chargePerHour) {
		        this.chargePerHour = chargePerHour;
		    }
		public User() {
			}
			
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
			public String getUserPassword() {
				return userPassword;
			}
			public void setUserPassword(String userPassword) {
				this.userPassword = userPassword;
			}
			public Set<UserRole> getRoles() {
				return roles;
			}
			public void setRoles(Set<UserRole> roles) {
				this.roles = roles;
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
			
			

			public User(Long id, String userName, @Email String userMail, String phoneNumber, String userPassword,
					Set<UserRole> roles, byte[] profileImage, String expertise, String location, String bio, String industry,
					String mentoringRequiredFor) {
				super();
				this.id = id;
				this.userName = userName;
				this.userMail = userMail;
				this.phoneNumber = phoneNumber;
				this.userPassword = userPassword;
				this.roles = roles;
				this.profileImage = profileImage;
				this.expertise = expertise;
				this.location = location;
				this.bio = bio;
				this.industry = industry;
				this.mentoringRequiredFor = mentoringRequiredFor;
			}
			public User(String userName, String userMail, Set<UserRole> roles) {
			    this.userMail = userMail;
			    this.roles = roles;
			}
		
			public User(String userMail, Set<UserRole> roles) {
			    super();
			    this.userMail = userMail;
			    this.roles = roles;
			}

			
			
	   
	}
