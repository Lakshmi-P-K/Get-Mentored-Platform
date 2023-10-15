package com.nineleaps.authentication.jwt.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class FeedbackAnalyticsDTO {
    private Double averageEngagementRating;
    private Double averageMenteeRating;
    private Double averageMentorRating;
    private Long positiveFeedbackCount;
    private Long negativeFeedbackCount;
    private Long neutralFeedbackCount;
    
    public FeedbackAnalyticsDTO() {
		
	}

	public Double getAverageEngagementRating() {
		return averageEngagementRating;
	}

	public void setAverageEngagementRating(Double averageEngagementRating) {
		this.averageEngagementRating = averageEngagementRating;
	}

	public Double getAverageMenteeRating() {
		return averageMenteeRating;
	}

	public void setAverageMenteeRating(Double averageMenteeRating) {
		this.averageMenteeRating = averageMenteeRating;
	}

	public Double getAverageMentorRating() {
		return averageMentorRating;
	}

	public void setAverageMentorRating(Double averageMentorRating) {
		this.averageMentorRating = averageMentorRating;
	}

	public Long getPositiveFeedbackCount() {
		return positiveFeedbackCount;
	}

	public void setPositiveFeedbackCount(Long positiveFeedbackCount) {
		this.positiveFeedbackCount = positiveFeedbackCount;
	}

	public Long getNegativeFeedbackCount() {
		return negativeFeedbackCount;
	}

	public void setNegativeFeedbackCount(Long negativeFeedbackCount) {
		this.negativeFeedbackCount = negativeFeedbackCount;
	}

	public Long getNeutralFeedbackCount() {
		return neutralFeedbackCount;
	}

	public void setNeutralFeedbackCount(Long neutralFeedbackCount) {
		this.neutralFeedbackCount = neutralFeedbackCount;
	}

	public FeedbackAnalyticsDTO(Double averageEngagementRating, Double averageMenteeRating, Double averageMentorRating,
			Long positiveFeedbackCount, Long negativeFeedbackCount, Long neutralFeedbackCount) {
		super();
		this.averageEngagementRating = averageEngagementRating;
		this.averageMenteeRating = averageMenteeRating;
		this.averageMentorRating = averageMentorRating;
		this.positiveFeedbackCount = positiveFeedbackCount;
		this.negativeFeedbackCount = negativeFeedbackCount;
		this.neutralFeedbackCount = neutralFeedbackCount;
	}
    

   
}
