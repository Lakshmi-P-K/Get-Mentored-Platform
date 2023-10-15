package com.nineleaps.authentication.jwt.dto;



import com.nineleaps.authentication.jwt.entity.User;

	
	
	public class SSOUserDto {
	    private Long id;
	    private String userMail;
	    private String userName;
	    
	    public SSOUserDto() {}
	    
	    public SSOUserDto(User user) {
	        this.id = user.getId();
	        this.userMail = user.getUserMail();
	        this.userName = user.getUserName();
	    }
	    public Long getId() {
	        return id;
	    }
	    
	    public void setId(Long id) {
	        this.id = id;
	    }
	    
	    public String getUserMail() {
	        return userMail;
	    }
	    
	    public void setUserMail(String userMail) {
	        this.userMail = userMail;
	    }
	    
	    public String getUserName() {
	        return userName;
	    }
	    
	    public void setUserName(String userName) {
	        this.userName = userName;
	    }
	}

	
	


