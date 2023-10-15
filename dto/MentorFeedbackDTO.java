package com.nineleaps.authentication.jwt.dto;

import java.time.LocalDateTime;

public class MentorFeedbackDTO {
        private Long id;
        private Double menteeRating;
        private String menteeFeedback;
        private Double engagementRating;
        private String engagementFeedback;
        private String feedbackFromUserName;
        private LocalDateTime createdTime;
		public MentorFeedbackDTO() {
			
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
		public Double getMenteeRating() {
			return menteeRating;
		}
		public void setMenteeRating(Double menteeRating) {
			this.menteeRating = menteeRating;
		}
		public String getMenteeFeedback() {
			return menteeFeedback;
		}
		public void setMenteeFeedback(String menteeFeedback) {
			this.menteeFeedback = menteeFeedback;
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
		