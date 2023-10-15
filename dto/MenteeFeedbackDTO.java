package com.nineleaps.authentication.jwt.dto;

import java.time.LocalDateTime;

public class MenteeFeedbackDTO {
    private Long id;
    private Double mentorRating;
    private String mentorFeedback;
    private Double engagementRating;
    private String engagementFeedback;
    private String feedbackFromUserName;
    private LocalDateTime createdTime;
    
    public MenteeFeedbackDTO() {
		
	}

    
	public LocalDateTime getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Double getMentorRating() {
		return mentorRating;
	}


	public void setMentorRating(Double mentorRating) {
		this.mentorRating = mentorRating;
	}


	public String getMentorFeedback() {
		return mentorFeedback;
	}


	public void setMentorFeedback(String mentorFeedback) {
		this.mentorFeedback = mentorFeedback;
	}


	public Double getEngagementRating() {
		return engagementRating;
	}


	public void setEngagementRating(Double engagementRating) {
		this.engagementRating = engagementRating;
	}


	public String getEngagementFeedback() {
		return engagementFeedback;
	}


	public void setEngagementFeedback(String engagementFeedback) {
		this.engagementFeedback = engagementFeedback;
	}


	public String getFeedbackFromUserName() {
		return feedbackFromUserName;
	}


	public void setFeedbackFromUserName(String feedbackFromUserName) {
		this.feedbackFromUserName = feedbackFromUserName;
	}
	
	
    
    
    
    
}
