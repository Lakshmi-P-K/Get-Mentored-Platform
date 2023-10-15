package com.nineleaps.authentication.jwt.exception;


public class ResourceNotFoundException extends Exception {
	

	    public ResourceNotFoundException(String message) {
	        super(message);
	    }
	    public ResourceNotFoundException(String message, Throwable cause) {
	        super(message, cause);
	    }
	    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue) {
	        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
	    }

	  
	        
	      
	   
}



