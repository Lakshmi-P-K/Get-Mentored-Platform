package com.nineleaps.authentication.jwt.dto;

public class ConnectionRequestStatisticsMentorDTO {
	
	    private int received;
	    private int accepted;
	    private int rejected;
	    private int pending;
	  
	    public ConnectionRequestStatisticsMentorDTO(int received, int accepted, int rejected, int pending) {
			super();
			this.received = received;
			this.accepted = accepted;
			this.rejected = rejected;
			this.pending = pending;
		}
		public int getReceived() {
	        return received;
	    }
	    public void setReceived(int received) {
	        this.received = received;
	    }
	    public int getAccepted() {
	        return accepted;
	    }
	    public void setAccepted(int accepted) {
	        this.accepted = accepted;
	    }
	    public int getRejected() {
	        return rejected;
	    }
	    public void setRejected(int rejected) {
	        this.rejected = rejected;
	    }
	    public int getPending() {
	        return pending;
	    }
	    public void setPending(int pending) {
	        this.pending = pending;
	    }
	}


